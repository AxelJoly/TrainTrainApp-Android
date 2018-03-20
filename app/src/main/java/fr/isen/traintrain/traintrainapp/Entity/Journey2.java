package fr.isen.traintrain.traintrainapp.Entity;

/**
 * Created by ruiz on 19/03/2018.
 */

public class Journey2 {

    public String departure;
    public String arrival;
    public String departureFormat;
    public String arrivalFormat;
    public String placeFrom;
    public String placeTo;

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String change;

    public String getDepartureFormat() {
        return departureFormat;
    }

    public void setDepartureFormat(String departureFormat) {
        departureFormat = timeDisplayDeparture(departureFormat);
        this.departureFormat = departureFormat;
    }

    public String getArrivalFormat() {
        return arrivalFormat;
    }

    public void setArrivalFormat(String arrivalFormat) {
        arrivalFormat = timeDisplayArrival(arrivalFormat);
        this.arrivalFormat = arrivalFormat;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }



    public String getDuration() {

        int hourDeparture = Integer.parseInt(getDeparture().substring(9, 11));
        int minutesDeparture = Integer.parseInt(getDeparture().substring(11, 13));
        int hourArrival = Integer.parseInt(getArrival().substring(9, 11));
        int minutesArrival = Integer.parseInt(getArrival().substring(11, 13));

        if (hourDeparture > hourArrival) {
            if (minutesDeparture > minutesArrival) {

                if (hourArrival + 23 - hourDeparture < 10) {

                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ("0" + (hourArrival + 23 - hourDeparture) + "h0" + (minutesArrival + 60 - minutesDeparture));
                    } else {
                        return ("0" + (hourArrival + 23 - hourDeparture) + "h" + (minutesArrival + 60 - minutesDeparture));
                    }
                } else {
                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ((hourArrival + 23 - hourDeparture) + "h0" + (minutesArrival + 60 - minutesDeparture));
                    } else {
                        return ((hourArrival + 23 - hourDeparture) + "h" + (minutesArrival + 60 - minutesDeparture));
                    }
                }

            } else {
                if (hourArrival + 24 - hourDeparture < 10) {

                    if (minutesArrival - minutesDeparture < 10) {
                        return ("0" + (hourArrival + 24 - hourDeparture) + "h0" + (minutesArrival - minutesDeparture));
                    } else {
                        return ("0" + (hourArrival + 24 - hourDeparture) + "h" + (minutesArrival - minutesDeparture));
                    }
                } else {
                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ((hourArrival + 24 - hourDeparture) + "h0" + (minutesArrival - minutesDeparture));
                    } else {
                        return ((hourArrival + 24 - hourDeparture) + "h" + (minutesArrival - minutesDeparture));
                    }
                }
                //return ((hourArrival+24-hourDeparture)+":"+(minutesArrival-minutesDeparture));
            }
        } else {
            if (minutesDeparture > minutesArrival) {

                if (hourArrival - 1 - hourDeparture < 10) {

                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ("0" + (hourArrival - 1 - hourDeparture) + "h0" + (minutesArrival + 60 - minutesDeparture));
                    } else {
                        return ("0" + (hourArrival - 1 - hourDeparture) + "h" + (minutesArrival + 60 - minutesDeparture));
                    }
                } else {
                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ((hourArrival - 1 - hourDeparture) + "h0" + (minutesArrival + 60 - minutesDeparture));
                    } else {
                        return ((hourArrival - 1 - hourDeparture) + "h" + (minutesArrival + 60 - minutesDeparture));
                    }
                }

            } else {
                if (hourArrival - hourDeparture < 10) {

                    if (minutesArrival - minutesDeparture < 10) {
                        return ("0" + (hourArrival - hourDeparture) + "h0" + (minutesArrival - minutesDeparture));
                    } else {
                        return ("0" + (hourArrival - hourDeparture) + "h" + (minutesArrival - minutesDeparture));
                    }
                } else {
                    if (minutesArrival + 60 - minutesDeparture < 10) {
                        return ((hourArrival - hourDeparture) + "h0" + (minutesArrival - minutesDeparture));
                    } else {
                        return ((hourArrival - hourDeparture) + "h" + (minutesArrival - minutesDeparture));
                    }
                }
            }
        }
    }


    public String timeDisplayDeparture(String string) {
        String day;
        String month;
        String year;
        String hour;
        String minutes;
        String res;

        year = string.substring(0, 4);
        month = string.substring(4, 6);
        day = string.substring(6, 8);
        hour = string.substring(9, 11);
        minutes = string.substring(11, 13);

        res = day + "/" + month + "/" + year + " de " + hour + ":" + minutes;

        return res;
    }
    public String timeDisplayArrival(String string) {
        String day;
        String month;
        String year;
        String hour;
        String minutes;
        String res;

        year = string.substring(0, 4);
        month = string.substring(4, 6);
        day = string.substring(6, 8);
        hour = string.substring(9, 11);
        minutes = string.substring(11, 13);

        res =  day + "/" + month + "/" + year + " à " + hour + ":" + minutes;

        return res;
    }


}
