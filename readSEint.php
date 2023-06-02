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

// Werte aus der Tabelle profil basierend auf profilid auslesen
$profilid = $_GET["profilid"];
$query = "SELECT * FROM profil WHERE profilID = $profilid";
$result = $conn->query($query);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $SEint = array();
    for ($i = 1; $i <= 36; $i++) {
        $SEint[$i - 1] = $row["item" . $i];
    }

    // SEint als JSON-Ausgabe senden
    echo json_encode($SEint);
} else {
    echo "Keine Daten in der Tabelle gefunden.";
}

// Datenbankverbindung schließen
$conn->close();
?>
