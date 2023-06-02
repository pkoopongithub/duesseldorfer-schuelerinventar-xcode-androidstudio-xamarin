package com.example.duesseldorferschuelerinventar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /********************************************************************************/
    /* Paul Koop Düsseldorfer Schülerinventar                                       */
    /* Selbsteinschätzung Normtabelle HS                                            */
    /********************************************************************************/
//Deklaration profilid
    int profilid = 0;

    //Deklaration Normtabelle N SE HS
    double [][] normSE ={
            {21.33,25.33,29.33,33.32,37.32},
            {20.87,24.95,29.03,33.10,37.18},
            {17.93,21.37,24.80,28.23,31.67},
            {13.98,17.71,21.44,25.17,28.90},
            {24.06,28.55,33.04,37.53,42.01},
            {15.53,18.97,22.40,25.83,29.27}


    };

    //Deklaration Punkte Item SE 1..36

    String [] items ={
                "Zuverlaessigkeit",
                "Arbeitstempo",
                "Arbeitsplanung",
                "Organisationsfähigkeit",
                "Geschicklichkeit",
                "Ordnung",
                "Sorgfalt",
                "Kreativitaet",
                "Problemlosefaehigkeit",
                "Abstarktionsvermoegen",
                "Selbststaendigkeit",
                "Belastbarkeit",
                "Konzentrationsfaehigkeit",
                "Verantwortungsbewusstsein",
                "Eigeninitiative",
                "Leistungsbereitschaft",
                "Auffassungsgabe",
                "Merkfaehigkeit",
                "Motivationsfaehigkeit",
                "Reflektionsfaehigkeit",
                "Teamfaehigkeit",
                "Hilfsbereitschaft",
                "Kontaktfaehigkeit",
                "RespektvollerUmgang",
                "Kommunikationsfaehigkeit",
                "Einfuehlungsvermoegen",
                "Konfliktfaehigkeit",
                "Kritikfaehigkeit",
                "Schreiben",
                "Lesen",
                "Mathematik",
                "Naturwissenschaften",
                "Fremdsprachen",
                "Praesentationsfaehigkeit",
                "PC-Kenntnisse",
                "FaecheruebergreifendesDenken"};

    //Deklaration Punkte Item SE 1..36 Vorbelegung 2 "selten"

    int [] SEint = {
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2
    };

    //Deklaration Punkte Kompetenzen 1..6

    int [][] SEPint = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};

    // Deklaration Profil Insert oder Update
    public int insertOrUpdateSEint(int profilid, int[] SEint) {
        try {
            String phpScriptUrl = "https://mein-duesk.org/insertOrUpdateSEint.php";
            String parameters = "?profilid=" + profilid + "&SEint=" + arrayToString(SEint);

            URL url = new URL(phpScriptUrl + parameters);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();

            reader.close();
            connection.disconnect();

            //System.out.println("Server Response: " + response);
            return Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    // Normtabelle einlesen
    public static double[][] readNormTable() {
        String phpScriptUrl = "https://mein-duesk.org/readNormTable.php";

        try {
            // Verbindung zum PHP-Skript herstellen
            URL url = new URL(phpScriptUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Antwort des Servers abrufen
            int responseCode = connection.getResponseCode();

            // Prüfen, ob die Anfrage erfolgreich war
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Antwort lesen
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                // Daten aus der Antwort parsen und in das normSE-Array einfügen
                String[] rows = response.toString().split(";");
                double[][] normSE = new double[rows.length][];
                for (int i = 0; i < rows.length; i++) {
                    String[] columns = rows[i].split(",");
                    normSE[i] = new double[columns.length];
                    for (int j = 0; j < columns.length; j++) {
                        normSE[i][j] = Double.parseDouble(columns[j]);
                    }
                }

                return normSE;
            } else {
                System.out.println("Fehler beim Abrufen der Normtabelle. Serverantwort: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //SEint Lesen
    public static int[] readSEint(int profilid) {
        String phpScriptUrl = "https://mein-duesk.org/readSEint.php?profilid=" + profilid;

        try {
            // Verbindung zum PHP-Skript herstellen
            URL url = new URL(phpScriptUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Antwort des Servers abrufen
            int responseCode = connection.getResponseCode();

            // Prüfen, ob die Anfrage erfolgreich war
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Antwort lesen
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                // Daten aus der Antwort parsen und in das SEint-Array einfügen
                String[] values = response.toString().split(",");
                int[] SEint = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    SEint[i] = Integer.parseInt(values[i]);
                }

                return SEint;
            } else {
                System.out.println("Fehler beim Abrufen der SEint-Werte. Serverantwort: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonImmer = findViewById(R.id.buttonImmer);
        buttonImmer.setOnClickListener(this);

        Button buttonMeistens = findViewById(R.id.buttonMeistens);
        buttonMeistens.setOnClickListener(this);

        Button buttonSelten = findViewById(R.id.buttonSelten);
        buttonSelten.setOnClickListener(this);

        Button buttonNie = findViewById(R.id.buttonNie);
        buttonNie.setOnClickListener(this);

        Button buttonWeiter = findViewById(R.id.buttonWeiter);
        buttonWeiter.setOnClickListener(this);

        double [][] normSEtest = readNormTable();
        if (normSEtest != null) {normSE = normSEtest;}


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {


        TextView  textItemNr = findViewById(R.id.textItemNr);
        TextView textItem = findViewById(R.id.textItem);
        TextView textBis = findViewById(R.id.textBis);

        int itemNr = Integer.parseInt(textItemNr.getText().toString());
        // textItemNr.setText(Integer.toString(itemNr));

        ProgressBar progressBarKompetenz1 = findViewById(R.id.progressBarKompetenz1);
        // progressBarKompetenz1.setProgress(x) und  0 <= x <= 100
        ProgressBar progressBarKompetenz2 = findViewById(R.id.progressBarKompetenz2);
        // progressBarKompetenz2.setProgress(x) und  0 <= x <= 100
        ProgressBar progressBarKompetenz3 = findViewById(R.id.progressBarKompetenz3);
        // progressBarKompetenz3.setProgress(x) und  0 <= x <= 100
        ProgressBar progressBarKompetenz4 = findViewById(R.id.progressBarKompetenz4);
        // progressBarKompetenz4.setProgress(x) und  0 <= x <= 100
        ProgressBar progressBarKompetenz5 = findViewById(R.id.progressBarKompetenz5);
        // progressBarKompetenz5.setProgress(x) und  0 <= x <= 100
        ProgressBar progressBarKompetenz6 = findViewById(R.id.progressBarKompetenz6);
        // progressBarKompetenz6.setProgress(x) und  0 <= x <= 100
        
        






      switch (view.getId()){
          case R.id.buttonImmer:
              //wenn itemNr >0 dann Itempunkt 4
              SEint[itemNr-1]=4;
              break;

          case R.id.buttonMeistens:
              //wenn itemNr >0 dann Itempunkt 3
              SEint[itemNr-1]=3;
              break;

          case R.id.buttonSelten:
              //wenn itemNr >0 dann Itempunkt 2
              SEint[itemNr-1]=2;
              break;

          case R.id.buttonNie:
              //wenn itemNr >0 dann Itempunkt 1
              SEint[itemNr-1]=1;
              break;

          case R.id.buttonWeiter:
              /*
                a itemNr +=1;
                b wenn itemNr > 36 dann itemNr =1;
                c Kompetenzen aus Items aufsummieren
                d Durch Vergleich mit Normtabelle Punkte für Kompetenz bestimmen
                e Stand Progressbar aktualisieren
               */

            //a
              itemNr +=1;
            //b
              if (itemNr >36) {
                  itemNr =1;
                  int profilidTest = insertOrUpdateSEint(profilid,SEint);
                  if (profilidTest > 0){profilid = profilidTest;}
                  int [] SEintTest = readSEint(profilid);
                  if (SEintTest != null){SEint = SEintTest;}


              }
              textItemNr.setText(Integer.toString(itemNr));
              textItem.setText(items[itemNr-1]);
            //c


              SEPint[0][0]=SEint[0]+ SEint[1]+ SEint[2]+ SEint[3]+ SEint[4]+
                      SEint[5]+ SEint[6]+ SEint[7]+ SEint[8]+ SEint[9];
              SEPint[1][0]=SEint[10]+ SEint[11]+ SEint[12]+ SEint[13]+ SEint[14]+
                      SEint[15]+ SEint[16]+ SEint[17]+ SEint[18]+ SEint[19];
              SEPint[2][0]=SEint[20]+ SEint[21]+ SEint[22]+ SEint[23]+ SEint[24]+
                      SEint[25]+ SEint[26]+ SEint[27]+ SEint[8]+ SEint[9];
              SEPint[3][0]=SEint[28]+ SEint[29]+ SEint[30]+ SEint[31]+ SEint[32]+
                      SEint[33]+ SEint[34]+ SEint[35];
              SEPint[4][0]=SEint[0]+ SEint[1]+
                      SEint[5]+ SEint[6]+ SEint[7]+ SEint[8]+ SEint[9]+
                      SEint[10]+SEint[11]+ SEint[13]+ SEint[14];
              SEPint[5][0]=SEint[2]+ SEint[3]+ SEint[4]+
                      SEint[8]+ SEint[9]+ SEint[10]+
                      SEint[16]+ SEint[17];
              //d
              boolean punkte=false;

              for (int k=0;k<=5;k++){
                  punkte=false;
                  for (int p=0;p<=4;p++){
                      if (SEPint[k][0]< (int) normSE[k][p]){
                          SEPint[k][1]=p+1;
                          punkte=true;
                          p=5;
                      }
                  }
                if (!punkte){
                    SEPint[k][1]=5;
                }
              }
              //e
              progressBarKompetenz1.setProgress(SEPint[0][1]*20);
              progressBarKompetenz2.setProgress(SEPint[1][1]*20);
              progressBarKompetenz3.setProgress(SEPint[2][1]*20);
              progressBarKompetenz4.setProgress(SEPint[3][1]*20);
              progressBarKompetenz5.setProgress(SEPint[4][1]*20);
              progressBarKompetenz6.setProgress(SEPint[5][1]*20);


              break;


      }
    }
}
