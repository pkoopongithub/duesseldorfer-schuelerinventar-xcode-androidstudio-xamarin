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

// Parameter aus der GET-Anfrage lesen
$profilid = $_GET["profilid"];
$SEint = explode(",", $_GET["SEint"]);

if ($profilid == 0) {
    // Neuer Eintrag in die Tabelle profil
    $insertQuery = "INSERT INTO profil (userID, gruppeID, item1, item2, ..., item36) VALUES (15, 1000, ";
    for ($i = 0; $i < 36; $i++) {
        $insertQuery .= $SEint[$i];
        if ($i < 35) {
            $insertQuery .= ",";
        }
    }
    $insertQuery .= ")";
    $conn->query($insertQuery);

    // ID des neu eingefügten Eintrags auslesen
    $profilid = $conn->insert_id;

    echo $profilid;
} else {
    // Aktualisierung eines bestehenden Eintrags in der Tabelle profil
    $updateQuery = "UPDATE profil SET ";
    for ($i = 0; $i < 36; $i++) {
        $updateQuery .= "item" . ($i + 1) . " = " . $SEint[$i];
        if ($i < 35) {
            $updateQuery .= ",";
        }
    }
    $updateQuery .= " WHERE profilID = $profilid";
    $conn->query($updateQuery);

    echo $profilid;
}

// Datenbankverbindung schließen
$conn->close();
?>
