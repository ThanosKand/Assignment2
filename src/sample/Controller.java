package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import java.sql.*;


public class Controller {

    private ObservableList list_of_stations = FXCollections.observableArrayList();

    // TrainModel m = new TrainModel();

    @FXML
    ChoiceBox<String> stations_From;
    @FXML
    ChoiceBox<String> stations_To;
    @FXML
    Button btn;
    @FXML
    TextField screen;
    @FXML
    Spinner<Object> timing;

    SelectRoute trip=new SelectRoute();

    private void loadStations() {
        list_of_stations.removeAll(list_of_stations);
        String a = "Kobenhavn H";
        String b = "Hoje Tastrup";
        String c = "Roskilde";
        String d = "Ringsted";
        String e = "Odense";
        String f = "Nastved";
        String g = "Nykobing F";
        list_of_stations.addAll(a, b, c, d, e, f, g);
        stations_From.getItems().addAll(list_of_stations);
        stations_To.getItems().addAll(list_of_stations);
    }

    private void loadSpinner(){
        timing.setId("list-spinner");
        List<Object> names = new ArrayList<Object>();
        names.add("0.00");
        names.add("0.30");
        names.add("1.00");
        names.add("1.30");
        names.add("2.00");
        names.add("2.30");
        names.add("3.00");
        names.add("3.30");
        names.add("4.00");
        names.add("4.30");
        names.add("5.00");
        names.add("5.30");
        names.add("6.00");
        names.add("6.30");
        names.add("7.00");
        names.add("7.30");
        names.add("8.00");
        names.add("8.30");
        names.add("9.00");
        names.add("9.30");
        names.add("10.00");
        names.add("10.30");
        names.add("11.00");
        names.add("11.30");
        timing.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Object>(FXCollections.observableArrayList(names)));
        // return timing;







       /* Spinner<Double> timing = new Spinner<>(0.0, 10.0, 0.0, 1.0);
        SpinnerValueFactory.DoubleSpinnerValueFactory dblFactory =
                (SpinnerValueFactory.DoubleSpinnerValueFactory) timing.getValueFactory();
        double dmin = dblFactory.getMin(); // 0.0
        double dmax = dblFactory.getMax(); // 10.0
        double dstep = dblFactory.getAmountToStepBy(); // 1.0 */





       /* SpinnerValueFactory<Double> valueFactory =
               new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00,24.00,0.00,1.00);
                timing.setValueFactory(valueFactory); */






       // Spinner<Object> spinner = new Spinner<>();









       /* StringConverter<Double> doubleConverter = new StringConverter<Double>() {
        private final DecimalFormat df = new DecimalFormat("#.##");
        @Override
        public String toString(Double object) {
            if (object == null) {return "";}
            return df.format(object);}
        @Override
        public Double fromString(String string) {
            try {
                if (string == null) {return null;}
                string = string.trim();
                if (string.length() < 1) {return null;}
                return df.parse(string).doubleValue();
            } catch (ParseException ex) {throw new RuntimeException(ex);}
        }
    };


    SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 24, 10.00, 1.00);
timing.setValueFactory(valueFactory);
timing.setEditable(true);
SpinnerValueFactory.setConverter(doubleConverter);


        */






          /*  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            timing.valueProperty().addListener((obs, oldTime, newTime) ->
                    System.out.println(formatter.format(newTime))); */


        }










       /* StringConverter<Double> doubleConverter = new StringConverter<Double>() {
            private final DecimalFormat df = new DecimalFormat("#.##");
            @Override
            public String toString(Double object) {
                if (object == null) {return "";}
                return df.format(object);}
            @Override
            public Double fromString(String string) {
                try {
                    if (string == null) {return null;}
                    string = string.trim();
                    if (string.length() < 1) {return null;}
                    return df.parse(string).doubleValue();
                } catch (ParseException ex) {throw new RuntimeException(ex);}
            }
        };

        Spinner<Double> spinner = new Spinner<Double>();
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 24,1.00 , 1.00);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);

        SpinnerValueFactory.setConverter(doubleConverter);
*/

    @FXML
    public void initialize() {
        loadStations();
        loadSpinner();
        btn.addEventHandler(ActionEvent.ACTION, event -> {
            String from = stations_From.getValue();
            String to = stations_To.getValue();

            Object text = timing.getValue();
            double t = Double.parseDouble((String) text);

            try{
                if (from.equals(to)) {
                    screen.setText("Try again");
                }else {
                    //screen.setText("Calculate route");
                    // screen.setText(trip.getTrip(9.00, 1,4));
                    screen.setText(from);
                    // screen.setText(to);
                  trip.getTrip(t ,from , to  );
                }


            } catch (NullPointerException e){
                screen.setText("Try again");
            }

        });
    }
}







   /* public void Present(ResultSet res)
            throws SQLException {
        if (res == null)
            System.out.println("No records for customer");
        while (res != null & res.next()) {
            String arrival = res.getString("arrivalTime");
            String train=res.getString("trainID");
            String station=res.getString("stationID");
            System.out.println("Train: " + train + "/ " + "Departure time: " + arrival );
            // System.out.println(arrival +"  " );
        }
    }


    public ResultSet takeRoute( Connection conn, double t, int to , int from)
            throws SQLException {
        String query =  " SELECT *" +
                "  FROM Arrivals AS start" +
                " JOIN Arrivals AS finish ON start.trainID = finish.trainID " +
                " WHERE start.arrivalTime > t AND " +
                "       start.arrivalTime < finish.arrivalTime AND " +
                "       start.stationID = '" + to + "' AND " +
                "       finish.stationID = '" + from + "'" +
                " ORDER BY arrivalTime";

        Statement stmt = null;
        ResultSet res = null;
        stmt = conn.createStatement();
        res = stmt.executeQuery(query);
        return res;
    }

    */






























