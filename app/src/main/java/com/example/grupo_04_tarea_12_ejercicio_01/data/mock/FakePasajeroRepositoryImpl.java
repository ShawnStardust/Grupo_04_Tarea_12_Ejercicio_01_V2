package com.example.grupo_04_tarea_12_ejercicio_01.data.mock;

import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.PasajeroRepository;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.Pasajero;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FakePasajeroRepositoryImpl implements PasajeroRepository {

    private List<Pasajero> pasajeros;
    private int nextId;

    @Inject
    public FakePasajeroRepositoryImpl() {
        this.pasajeros = new ArrayList<>(MockDataProvider.getPasajeros());
        this.nextId = pasajeros.stream()
                .mapToInt(Pasajero::getIdPasajero)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void insert(Pasajero pasajero) {
        if (pasajero.getIdPasajero() == 0) {
            pasajero.setIdPasajero(nextId++);
        }
        pasajeros.add(pasajero);
    }

    @Override
    public void update(Pasajero pasajero) {
        for (int i = 0; i < pasajeros.size(); i++) {
            if (pasajeros.get(i).getIdPasajero() == pasajero.getIdPasajero()) {
                pasajeros.set(i, pasajero);
                break;
            }
        }
    }

    @Override
    public void delete(Pasajero pasajero) {
        pasajeros.removeIf(p -> p.getIdPasajero() == pasajero.getIdPasajero());
    }

    @Override
    public List<Pasajero> getAll() {
        return new ArrayList<>(pasajeros);
    }

    @Override
    public Pasajero getById(int id) {
        return pasajeros.stream()
                .filter(p -> p.getIdPasajero() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pasajero getByEmail(String email) {
        return pasajeros.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pasajero getByDocumento(String numDocumento) {
        return pasajeros.stream()
                .filter(p -> p.getNumDocumento().equalsIgnoreCase(numDocumento))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pasajero login(String email, String clave) {
        return pasajeros.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email)
                        && p.getClave().equals(clave))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteAll() {
        pasajeros.clear();
    }
}
