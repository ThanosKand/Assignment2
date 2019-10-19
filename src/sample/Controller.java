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



class SelectRoute {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/PC/Desktop/ExamplesSQL/ass6.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void getTrip(double tim, String from , String to){

          int f=tranf_f(from);
          int t=tranf_t(to);

        String sql = " SELECT *" +
                "  FROM Arrivals AS start" +
                " JOIN Arrivals AS finish ON start.trainID = finish.trainID " +
                " WHERE start.arrivalTime >= ? AND " +
                "       start.arrivalTime < finish.arrivalTime AND " +
                "       start.stationID = ? AND " +
                "       finish.stationID = ?" +
                " ORDER BY arrivalTime";

        //  "  JOIN Trains ON Trains.trainID = Arrivals.trainID " +
        // "  JOIN Stations ON Stations.stationID = Arrivals.stationID" ;

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setDouble(1, tim);
            pstmt.setInt(2, f);
            pstmt.setInt(3, t);
            //
            ResultSet rs  = pstmt.executeQuery();

          //  ArrayList<String> tt= new ArrayList<>();
            // loop through the result set
            if (!rs.next()) {
                System.out.println("Sorry, there is no scheduled route!");
            }

            while (rs.next()) {

               System.out.println(
                        "TrainID: "+
                                rs.getString("trainID") + "\t" +
                                "/Departure Time:"  +
                                rs.getDouble("arrivalTime") +  "\t");



              // tt.add(rs.getString("trainID"));




               //tt.add(rs.getString("trainID"));
                //String tt=rs.getString("trainID") + "\t";
                //return tt;
               // Double aa= rs.getDouble("arrivalTime");


                // rs.getString("stationID"));
            }

          //  return tt;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }



    public int tranf_f(String from) {
        int f=-1;
        switch (from) {
            case "Kobenhavn H":
                f = 1;
                break;
            case "Hoje Tastrup":
                f = 2;
                break;
            case "Roskilde":
                f = 3;
                break;
            case "Ringsted":
                f = 4;
                break;
            case "Odense":
                f = 5;
                break;
            case "Nastved":
                f = 6;
                break;
            case "Nykobing F":
                f = 7;
                break;
        }
        return f;
    }

    public int tranf_t(String to) {
        int t=-1;
        switch (to) {
            case "Kobenhavn H":
                t = 1;
                break;
            case "Hoje Tastrup":
                t = 2;
                break;
            case "Roskilde":
                t = 3;
                break;
            case "Ringsted":
                t = 4;
                break;
            case "Odense":
                t = 5;
                break;
            case "Nastved":
                t = 6;
                break;
            case "Nykobing F":
                t = 7;
                break;
        }
        return t;
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
}


























   /* import java.sql.*;
    import java.util.Scanner;


public class CourseJDBC {

    public Connection connect(String url)
            throws SQLException, SQLException {
        return DriverManager.getConnection(url);
    }

    public void PresentStudents(ResultSet res)
            throws SQLException {
        if (res == null)
            System.out.println("No records for customer");
        while (res != null & res.next()) {
            String name = res.getString("Name");
            System.out.println(name);
        }
    }

    public ResultSet plainstatement(String student, Connection conn)
            throws SQLException {
        String query = "select Students.Name As Name, Courses.Name As Course, Study.Grade As Grade from Study" +
                " INNER JOIN Students ON Students.Studentid = Study.Studentid " +
                "INNER JOIN Courses ON Courses.Courseid = Study.Courseid" +
                " Where Students.Name = '" + student + "'";
        Statement stmt = null;
        ResultSet res = null;
        stmt = conn.createStatement();
        res = stmt.executeQuery(query);
        return res;
    }

    public PreparedStatement selectpreparedstatement(Connection conn)
            throws SQLException {
        String query = "select Students.Name As Name, Courses.Name As Course, Study.Grade As Grade from Study" +
                " INNER JOIN Students ON Students.Studentid = Study.Studentid " +
                "INNER JOIN Courses ON Courses.Courseid = Study.Courseid" +
                " Where Students.Name = ? ";
        PreparedStatement selectpstmt = null;
        selectpstmt = conn.prepareStatement(query);
        return selectpstmt;
    }

    public PreparedStatement Updatepreparedstatement(Connection conn)
            throws SQLException {
        String sqlUpdate = "UPDATE Study "
                + " SET Grade = ? "
                + " WHERE (Studentid, Courseid) "
                + " IN ( SELECT s.Studentid,c.Courseid "
                + " FROM Students As s, Courses As c"
                + " WHERE s.Name= ? AND c.Name = ? )";
        PreparedStatement updatepstmt = null;
        updatepstmt = conn.prepareStatement(sqlUpdate);
        return updatepstmt;
    }

    public PreparedStatement InsertStudentpreparedstatement(Connection conn)
            throws SQLException {
        String sqlInsert = "INSERT INTO Students (name, town , age, sex) VALUES(?,?,?,?)";
        PreparedStatement insertpstmt = null;
        insertpstmt = conn.prepareStatement(sqlInsert);
        return insertpstmt;
    }

    public PreparedStatement InsertCoursepreparedstatement(Connection conn)
            throws SQLException {
        String sqlInsert = "INSERT INTO Courses (Courseid,Name, Teacher, Description, Semester) VALUES(?,?,?,?,?)";
        PreparedStatement insertpstmt = null;
        insertpstmt = conn.prepareStatement(sqlInsert);
        return insertpstmt;
    }

    public PreparedStatement InsertStudypreparedstatement(Connection conn)
            throws SQLException {
        String sqlInsert = "INSERT INTO Study (Studentid, Courseid, Grade) SELECT s.Studentid,c.Courseid, ? " +
                "    FROM Students As s, Courses As c " +
                "        WHERE s.Name= ? AND c.Name = ? ;";
        PreparedStatement insertpstmt = null;
        insertpstmt = conn.prepareStatement(sqlInsert);
        return insertpstmt;
    }


    public static void main(String[] args) {
        CourseJDBC CJ = new CourseJDBC();
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/Users/liner/Documents/SD2019E/SQLiteDatabases/Enrollment.db";
            conn = CJ.connect(url);
// con.setAutoCommit (false);
            //Select;
            System.out.println("Which student do your wish to find?");
            Scanner scanner = new Scanner(System.in);
            String student = scanner.nextLine();
            System.out.println("Which course do your wish to find?");
            String course = scanner.nextLine();
            System.out.println("Which grade did the student get?");
            int scgrade = scanner.nextInt();
            PreparedStatement pstmt = CJ.Updatepreparedstatement(conn);
            pstmt.setInt(1, scgrade);
            pstmt.setString(2, student);
            pstmt.setString(3, course);
            int rowAffected = pstmt.executeUpdate();
            System.out.println(String.format("Rows affected %d", rowAffected));
            ResultSet res = CJ.plainstatement(student, conn);
            CJ.PresentStudents(res);
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
} */


















