package com.example.grupo_04_tarea_12_ejercicio_01.di;

import com.example.grupo_04_tarea_12_ejercicio_01.data.mock.FakePagoRepositoryImpl;
import com.example.grupo_04_tarea_12_ejercicio_01.data.mock.FakeReservaRepositoryImpl;
import com.example.grupo_04_tarea_12_ejercicio_01.data.mock.FakeTarifaRepositoryImpl;
import com.example.grupo_04_tarea_12_ejercicio_01.data.mock.FakeVueloRepositoryImpl;
import com.example.grupo_04_tarea_12_ejercicio_01.data.repository.*;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.Repository.*;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract PasajeroRepository bindPasajeroRepository(
            PasajeroRepositoryImpl pasajeroRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract PaisRepository bindPaisRepository(
            PaisRepositoryImpl paisRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract ReservaRepository bindReservaRepository(
            FakeReservaRepositoryImpl reservaRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract PagoRepository bindPagoRepository(
            FakePagoRepositoryImpl pagoRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract VueloRepository bindVueloRepository(
            FakeVueloRepositoryImpl vueloRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract AsientoRepository bindAsientoRepository(
            AsientoRepositoryImpl asientoRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract TarifaRepository bindTarifaRepository(
            FakeTarifaRepositoryImpl tarifaRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract AvionRepository bindAvionRepository(
            AvionRepositoryImpl avionRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract AeropuertoRepository bindAeropuertoRepository(
            AeropuertoRepositoryImpl aeropuertoRepositoryImpl
    );

    @Binds
    @Singleton
    public abstract AerolineaRepository bindAerolineaRepository(
            AerolineaRepositoryImpl aerolineaRepositoryImpl
    );
}