package com.jhiltunen.sensorlympics.utils

class OlympicCity(
    val city: String,
    val latiTude: Double,
    val longiTude: Double
)

object GlobalModel {
    val cities: MutableList<OlympicCity> = java.util.ArrayList()

    init {
        cities.add(OlympicCity("Albertville", 45.66688284656322, 6.373199746930923))
        cities.add(OlympicCity("Amsterdam", 52.36858962689727, 4.861367077111594))
        cities.add(OlympicCity("Antwerp", 51.2242881518194, 4.419565166677859))
        cities.add(OlympicCity("Athens", 37.99108690965441, 23.73353111782726))
        cities.add(OlympicCity("Beijing", 39.901718222468766, 116.41932651320408))
        cities.add(OlympicCity("Berlin", 52.53492251779334, 13.380765207013207))
        cities.add(OlympicCity("Brisbane", -27.445253818196054, 153.0261280382137))
        cities.add(OlympicCity("Calgary", 51.05837064377246, -114.0590178072199))
        cities.add(OlympicCity("Chamonix", 45.92281718120921, 6.863858761303383))
        cities.add(OlympicCity("Cortina d’Ampezzo", 46.540319615346306, 12.136474628829303))
        cities.add(OlympicCity("Garmisch-Partenkirchen", 47.488438721160804, 11.059927562644003))
        cities.add(OlympicCity("Grenoble", 45.194632284748124, 5.725324305071014))
        cities.add(OlympicCity("Helsinki", 60.182329113252294, 24.940385241352597))
        cities.add(OlympicCity("Innsbruck", 47.27402068329673, 11.39389463952134))
        cities.add(OlympicCity("Lake Placid", 44.285095988799675, -73.98535769345801))
        cities.add(OlympicCity("Lillehammer", 61.118363294161796, 10.466228663698502))
        cities.add(OlympicCity("London", 51.52916074600362, -0.13732590015318769))
        cities.add(OlympicCity("Los Angeles", 34.13049721304426, -118.29503135747481))
        cities.add(OlympicCity("Melbourne", -37.786959242409175, 144.9898721303202))
        cities.add(OlympicCity("Mexico City", 19.442325605345438, -99.19445659085474))
        cities.add(OlympicCity("Milan", 45.470352829182985, 9.195578865393271))
        cities.add(OlympicCity("Montreal", 45.56346705655108, -73.55179899215695))
        cities.add(OlympicCity("Moscow", 55.777413544438616, 37.657360728210186))
        cities.add(OlympicCity("Munich", 48.170140941372786, 11.517093314416595))
        cities.add(OlympicCity("Nagano", 36.67215087885699, 138.18177724177627))
        cities.add(OlympicCity("Oslo", 48.170140941372786, 11.517093314416595))
        cities.add(OlympicCity("Paris", 48.890833995917546, 2.3422226552958314))
        cities.add(OlympicCity("Pyeongchang-gun", 37.56496430064215, 128.47785857187858))
        cities.add(OlympicCity("Rio De Janeiro", -22.905552934772775, -43.20989403743441))
        cities.add(OlympicCity("Rome", 41.89950812213784, 12.500266554521628))
        cities.add(OlympicCity("Salt Lake City", 40.78770595219112, -111.89441874072116))
        cities.add(OlympicCity("Sapporo", 43.11445045116769, 141.38939610395576))
        cities.add(OlympicCity("Sarajevo", 43.86839636817344, 18.433110301885748))
        cities.add(OlympicCity("Seoul", 37.5646120483271, 126.9877918151757))
        cities.add(OlympicCity("Sochi", 43.60368892719333, 39.735083712845054))
        cities.add(OlympicCity("Squaw Valley", 39.19941612235193, -120.23615189245847))
        cities.add(OlympicCity("St Louis", 38.62787220651336, -90.18338222401577))
        cities.add(OlympicCity("St. Moritz", 46.487501783101266, 9.833384320297714))
        cities.add(OlympicCity("Stockholm", 59.34405043191012, 18.06580532136084))
        cities.add(OlympicCity("Sydney", -33.8451754800854, 151.17412228313322))
        cities.add(OlympicCity("Tokyo", 35.68493924198597, 139.76801835246332))
        cities.add(OlympicCity("Turin", 45.06903836909705, 7.678346927901791))
        cities.add(OlympicCity("Vancouver", 49.29438458373852, -123.10949023081135))

        cities.sortByDescending { it.city }
    }
}