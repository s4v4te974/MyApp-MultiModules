package org.flight.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FlightConsts {

    public static final String UNABLE_TO_RETRIEVE_CITIES = "Unable to retrieve cities";

    public static final double TAX_AV_CIVILE_FR = 4.4;

    public static final double TAX_AV_CIVILE_OTHER = 7.92;

    public static final double SECURITY_TAX_MAX = 9.5;

    public static final double REDEVANCE_PASSENGER_MAX = 13.0;

    public static final double SECURITY_TAX = 3 + Math.random() * (SECURITY_TAX_MAX - 3);

    public static final double REDEVANCE_PASSENGER = 2.6 + Math.random() * (REDEVANCE_PASSENGER_MAX - 2.6);

    public static final double SOLIDARITY_TAX_UE = 1.13;

    public static final double SOLIDARITY_TAX_NON_UE = 4.5;

    public static final double SOLIDARITY_TAX_UE_MAX = 11.27;

    public static final double SOLIDARITY_TAX_NON_UE_MAX = 45.07;

    public static final double PRESTATION_ECONOMIC_UE = 25.0;

    public static final double PRESTATION_ECONOMICS = 50.0;
    
    public static final double PRESTATION_AFFAIR_UE = 100.0;

    public static final double PRESTATION_AFFAIR = 200.0;

    public static final double PRESTATION_FIRST_UE = 800.0;
    
    public static final double PRESTATION_FIRST = 2500.0 ;

    public static final String UNABLE_TO_FIND_PLANE = "Unable to retrieve planes";

}