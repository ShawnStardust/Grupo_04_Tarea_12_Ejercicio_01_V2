package com.example.grupo_04_tarea_12_ejercicio_01.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.grupo_04_tarea_12_ejercicio_01.data.local.dao.*;
import com.example.grupo_04_tarea_12_ejercicio_01.domain.model.*;
import com.example.grupo_04_tarea_12_ejercicio_01.utils.Converters;

@Database(
        entities = {
                Pasajero.class,
                Reserva.class,
                Pago.class,
                Vuelo.class,
                Asiento.class,
                Tarifa.class,
                Avion.class,
                Aeropuerto.class,
                Pais.class,
                Aerolinea.class
        },
        version = 2,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase INSTANCE;
    private static final String DATABASE_NAME = "airline_database";

    public abstract PasajeroDao pasajeroDao();
    public abstract ReservaDao reservaDao();
    public abstract PagoDao pagoDao();
    public abstract VueloDao vueloDao();
    public abstract AsientoDao asientoDao();
    public abstract TarifaDao tarifaDao();
    public abstract AvionDao avionDao();
    public abstract AeropuertoDao aeropuertoDao();
    public abstract PaisDao paisDao();
    public abstract AerolineaDao aerolineaDao();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDataBase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}