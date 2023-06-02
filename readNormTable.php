<?php
// Datenbankverbindung herstellen
$dbhost = "rdbms.strato.de";
$dbname = "DB3517771";
$dbuser = "U3517771";
$dbpass = "dBkNpI1000Jn";

$conn = new mysqli($dbhost, $dbuser, $dbpass, $dbname);
if ($conn->connect_error) {
    die("Verbindung zur Datenbank fehlgeschlagen: " . $conn->connect_error);
}

// Normtabelle aus Datenbank lesen und in normSE[][] einlesen
$query = "SELECT * FROM normSEhs ORDER BY kompetenzID";
$result = $conn->query($query);

if ($result->num_rows > 0) {
    $rowIndex = 0;
    while ($row = $result->fetch_assoc()) {
        $normSE[$rowIndex][0] = $row["p1"];
        $normSE[$rowIndex][1] = $row["p2"];
        $normSE[$rowIndex][2] = $row["p3"];
        $normSE[$rowIndex][3] = $row["p4"];
        $normSE[$rowIndex][4] = $row["p5"];
        $rowIndex++;
    }
} else {
    echo "Keine Daten in der Tabelle gefunden.";
}

// Datenbankverbindung schließen
$conn->close();

// normSE[][] als JSON-Ausgabe senden
echo json_encode($normSE);
?>
