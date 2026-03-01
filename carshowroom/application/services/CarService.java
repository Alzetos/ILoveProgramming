package ru.chekhet.carshowroom.application.services;

import lombok.RequiredArgsConstructor;
import ru.chekhet.carshowroom.application.abstractions.persistence.IPersistenceContext;
import ru.chekhet.carshowroom.application.abstractions.persistence.queries.*;
import ru.chekhet.carshowroom.application.contracts.cars.ICarService;
import ru.chekhet.carshowroom.application.contracts.cars.models.CarInstanceDto;
import ru.chekhet.carshowroom.application.contracts.cars.operations.*;
import ru.chekhet.carshowroom.application.services.mapping.CarMappingExtensions;
import ru.chekhet.carshowroom.domain.carconfigurators.CarConfigurator;
import ru.chekhet.carshowroom.domain.cars.*;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.specs.*;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.*;
import ru.chekhet.carshowroom.domain.cars.valueobjects.*;
import ru.chekhet.carshowroom.domain.valueobjects.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CarService implements ICarService {

    private final IPersistenceContext persistence;

    @Override
    public CreateCarModel.Response createModel(CreateCarModel.Request request) {
        try {
            CarModelId modelId = new CarModelId(System.currentTimeMillis());

            Map<ComponentCategory, CarComponent> baseConfig = new HashMap<>();
            for (var entry : request.baseComponentIds().entrySet()) {
                ComponentId compId = new ComponentId(entry.getValue());
                CarComponent comp = persistence.getCarComponents()
                        .query(CarComponentQuery.builder().ids(List.of(compId)).build())
                        .stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("Base component not found: " + entry.getKey()));

                baseConfig.put(new ComponentCategory(entry.getKey()), comp);
            }

            CarModel model = new CarModel(
                    modelId, request.brand(), request.modelName(), new Money(request.basePrice()),
                    new EngineCapacity(request.engineCapacity()), new EnginePower(request.enginePower()),
                    BodyType.valueOf(request.bodyType().toUpperCase()),
                    FuelType.valueOf(request.fuelType().toUpperCase()),
                    DriveType.valueOf(request.driveType().toUpperCase()),
                    TransmissionType.valueOf(request.transmissionType().toUpperCase()),
                    request.requiredCategories().stream().map(ComponentCategory::new).collect(Collectors.toSet()),
                    baseConfig
            );

            persistence.getCarModels().add(model);
            return new CreateCarModel.Response.Success(CarMappingExtensions.mapToDto(model));
        } catch (Exception e) {
            return new CreateCarModel.Response.Failure(e.getMessage());
        }
    }

    @Override
    public CreateCarComponent.Response createComponent(CreateCarComponent.Request request) {
        try {
            CarComponent component = new CarComponent(
                    new ComponentId(System.currentTimeMillis()),
                    new ComponentCategory(request.category().toUpperCase()),
                    request.name(),
                    new Money(request.extraPrice()),
                    request.compatibleModelIds().stream()
                            .map(CarModelId::new)
                            .collect(Collectors.toSet())
            );

            persistence.getCarComponents().add(component);
            return new CreateCarComponent.Response.Success(CarMappingExtensions.mapToDto(component));
        } catch (Exception e) {
            return new CreateCarComponent.Response.Failure("Failed to create component: " + e.getMessage());
        }
    }

    @Override
    public AddCarInstance.Response addInstance(AddCarInstance.Request request) {
        try {
            CarModel model = persistence.getCarModels()
                    .query(CarModelQuery.builder()
                            .ids(List.of(new CarModelId(request.carModelId())))
                            .build())
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Car model not found with ID: " + request.carModelId()));

            List<CarComponent> components = persistence.getCarComponents()
                    .query(CarComponentQuery.builder()
                            .ids(request.installedComponentIds().stream()
                                    .map(ComponentId::new)
                                    .collect(Collectors.toList()))
                            .build());

            CarConfigurator configurator = new CarConfigurator(model);
            for (CarComponent comp : components) {
                configurator.selectComponent(comp);
            }

            CustomConfiguration configuration = configurator.build();

            CarInstance instance = CarInstance.builder()
                    .id(new CarId(System.currentTimeMillis()))
                    .configuration(configuration)
                    .color(new Color(request.colorHex()))
                    .build();

            persistence.getCarInstances().add(instance);
            return new AddCarInstance.Response.Success(CarMappingExtensions.mapToDto(instance));
        } catch (Exception e) {
            return new AddCarInstance.Response.Failure("Failed to add car to stock: " + e.getMessage());
        }
    }

    @Override
    public GetCatalog.Response getCatalog(GetCatalog.Request request) {
        CarInstanceQuery query = CarInstanceQuery.builder()
                .brands(request.brand() != null ? List.of(request.brand()) : null)
                .modelNames(request.modelName() != null ? List.of(request.modelName()) : null)
                .bodyTypes(request.bodyType() != null ?
                        List.of(BodyType.valueOf(request.bodyType().toUpperCase())) : null)
                .fuelTypes(request.fuelType() != null ?
                        List.of(FuelType.valueOf(request.fuelType().toUpperCase())) : null)
                .transmissionTypes(request.transmissionType() != null ?
                        List.of(TransmissionType.valueOf(request.transmissionType().toUpperCase())) : null)
                .driveTypes(request.driveType() != null ?
                        List.of(DriveType.valueOf(request.driveType().toUpperCase())) : null)
                .enginePowers(request.minPower() != null ?
                        List.of(new EnginePower(request.minPower())) : null)
                .engineCapacities(request.minCapacity() != null ?
                        List.of(new EngineCapacity(request.minCapacity())) : null)
                .colors(request.colorHex() != null ?
                        List.of(new Color(request.colorHex())) : null)
                .build();

        List<CarInstance> domainCars = persistence.getCarInstances().query(query);

        List<CarInstanceDto> dtoList = domainCars.stream()
                .filter(car -> request.minPrice() == 0 || car.getPrice().value() >= request.minPrice())
                .filter(car -> request.maxPrice() == 0 || car.getPrice().value() <= request.maxPrice())
                .map(CarMappingExtensions::mapToDto)
                .collect(Collectors.toList());

        return new GetCatalog.Response(dtoList);
    }

    @Override
    public GetCarDetails.Response getDetails(GetCarDetails.Request request) {
        return persistence.getCarInstances()
                .query(CarInstanceQuery.builder()
                        .ids(List.of(new CarId(request.carId())))
                        .build())
                .stream()
                .findFirst()
                .<GetCarDetails.Response>map(car -> new GetCarDetails.Response.Success(CarMappingExtensions.mapToDto(car)))
                .orElse(new GetCarDetails.Response.Failure("Car with ID " + request.carId() + " not found."));
    }
}