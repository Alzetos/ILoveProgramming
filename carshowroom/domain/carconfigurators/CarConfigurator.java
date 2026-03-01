package ru.chekhet.carshowroom.domain.carconfigurators;

import ru.chekhet.carshowroom.domain.cars.CarModel;
import ru.chekhet.carshowroom.domain.cars.CustomConfiguration;
import ru.chekhet.carshowroom.domain.cars.components.CarComponent;
import ru.chekhet.carshowroom.domain.cars.components.valueobjects.ComponentCategory;
import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;
import ru.chekhet.carshowroom.domain.exceptions.IncompatibleComponentException;

import java.util.HashMap;
import java.util.Map;

public class CarConfigurator {
    private final CarModel model;
    private final Map<ComponentCategory, CarComponent> currentSelection;

    public CarConfigurator(CarModel model) {
        this.model = model;
        this.currentSelection = new HashMap<>();

        for (ComponentCategory category : model.getAvailableCategories()) {
            currentSelection.put(category, model.getBaseComponentFor(category));
        }
    }

    public void selectComponent(CarComponent component) {
        if (!model.getAvailableCategories().contains(component.category())) {
            throw new DomainValidationException("Model does not support component category: " + component.category().value());
        }

        if (!component.isCompatibleWith(model.getId())) {
            throw new IncompatibleComponentException("Component " + component.name() + " is incompatible with model: " + model.getModelName());
        }

        currentSelection.put(component.category(), component);
    }

    public void removeComponent(ComponentCategory category) {
        if (model.isCategoryRequired(category)) {
            throw new DomainValidationException("Can not delete required category " + category.value());
        }

        currentSelection.remove(category);
    }

    public CustomConfiguration build() {
        model.getRequiredCategories().forEach(category -> {
            if (!currentSelection.containsKey(category)) {
                throw new DomainValidationException("A required node is missing: " + category.value());
            }
        });

        return CustomConfiguration.builder()
                .model(model)
                .selectedComponents(Map.copyOf(currentSelection))
                .build();
    }
}
