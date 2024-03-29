//
//  ViewController.swift
//  DuesseldorferSchuelerinventarSwift
//
//  Created by Paul Koop on 30.04.20.
//  Copyright © 2020 Paul Koop. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    
    //Deklaration Normtabelle N SE HS
    var normSE = [
        [21.33,25.33,29.33,33.32,37.3],
        [20.87,24.95,29.03,33.10,37.18],
        [17.93,21.37,24.80,28.23,31.67],
        [13.98,17.71,21.44,25.17,28.90],
        [24.06,28.55,33.04,37.53,42.01],
        [15.53,18.97,22.40,25.83,29.27]
    ];
    //Deklaration Itembezeichner
    var items = [
        "Zuverlässigkeit",
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
        "FaecheruebergreifendesDenken"
    ];
    //Deklaration Punkte Item SE 1..36 Vorbelegung 2 "selten"
    var SEint = [
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
    ];
    //Deklaration Punkte Kompetenzen 1..6
    var SEPint = [[ 0, 0 ], [ 0, 0 ], [ 0, 0 ], [ 0, 0 ], [ 0, 0 ], [ 0,0 ]];
    
    //Deklaration Itemnummer
    var itemNr = 1
    //Deklaration Profilid
    var profilid = 0
    
    //Deklaration Normtabelle lesen

    func readNormTable() -> [[Double]] {
        let phpScriptUrl = "https://mein-duesk.org/readNormTable.php"
        
        guard let url = URL(string: phpScriptUrl) else {
            print("Ungültige URL: \(phpScriptUrl)")
            return []
        }
        
        do {
            let data = try Data(contentsOf: url)
            let jsonDecoder = JSONDecoder()
            let normSE = try jsonDecoder.decode([[Double]].self, from: data)
            return normSE
        } catch {
            print("Fehler beim Lesen der Normtabelle: \(error.localizedDescription)")
            return []
        }
    }
    
    //Deklaration Prolil lesen
    func readSEint(profilid: Int) -> [Int] {
        let phpScriptUrl = "https://mein-duesk.org/readSEint.php?profilid=\(profilid)"
        
        guard let url = URL(string: phpScriptUrl) else {
            print("Ungültige URL: \(phpScriptUrl)")
            return []
        }
        
        do {
            let data = try Data(contentsOf: url)
            let jsonDecoder = JSONDecoder()
            let SEint = try jsonDecoder.decode([Int].self, from: data)
            return SEint
        } catch {
            print("Fehler beim Lesen der SEint-Daten: \(error.localizedDescription)")
            return []
        }
    }
    
    //Deklaration Profil Einfuegen updaten
    func insertOrUpdateSEint(SEint: [Int], profilid: Int) -> Int? {
        var newProfilID: Int?
        let semaphore = DispatchSemaphore(value: 0)
        
        // Erstelle die URL für die PHP-Datei
        guard let url = URL(string: "https://mein-duesk.org/insertOrUpdateSEint.php") else {
            print("Fehler: Ungültige URL")
            return nil
        }
        
        // Erstelle die Anfrage
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        // Füge die Daten als HTTP-Body hinzu
        let parameters: [String: Any] = [
            "SEint": SEint,
            "profilid": profilid
        ]
        do {
            request.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: [])
        } catch {
            print("Fehler: Fehler beim Serialisieren der Daten")
            return nil
        }
        
        // Führe die Anfrage aus
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data, error == nil else {
                print("Fehler: \(error?.localizedDescription ?? "Unbekannter Fehler")")
                semaphore.signal()
                return
            }
            
            // Verarbeite die Antwort
            if let responseString = String(data: data, encoding: .utf8) {
                if let profilID = Int(responseString) {
                    newProfilID = profilID
                } else {
                    print("Fehler: Ungültige Profil-ID")
                }
            } else {
                print("Fehler: Ungültige Antwort")
            }
            
            semaphore.signal()
        }
        
        task.resume()
        semaphore.wait()
        
        return newProfilID
    }

    
    //---------------------------------------------
    
    @IBOutlet weak var textItemNr: UITextField!
    
    @IBOutlet weak var textItem: UITextField!
    
    @IBOutlet weak var ProgressViewKompetenz1: UIProgressView!
    
    @IBOutlet weak var ProgressViewKompetenz2: UIProgressView!
    
    @IBOutlet weak var ProgressViewKompetenz3: UIProgressView!
    
    @IBOutlet weak var ProgressViewKompetenz4: UIProgressView!
    
    @IBOutlet weak var ProgressViewKompetenz5: UIProgressView!
    
    @IBOutlet weak var ProgressViewKompetenz6: UIProgressView!
    
    
    @IBAction func ButtonWeiter(_ sender: UIButton) {
        /*
         a itemNr +=1;
         b wenn itemNr > 36 dann itemNr =1;
         c Kompetenzen aus Items aufsummieren
         d Durch Vergleich mit Normtabelle Punkte für Kompetenz bestimmen
         e Stand Progressbar aktualisieren
         */
        
        //a
        itemNr += 1;
        
        //b
        if (itemNr > 36) { itemNr = 1;
            let profilid = insertOrUpdateSEint(SEint: SEint, profilid: profilid);
            if (profilid > 0) {let SEinttest = readSEint(profilid: profilid);
                if (SEinttest != nil) {
                    SEint = SEinttest}
            }
                    
        }
        textItem.text = items[itemNr-1];
        textItemNr.text = String(itemNr);
        
            //c
        
        SEPint[0][0] = SEint[0] + SEint[1] + SEint[2] + SEint[3] + SEint[4] +
            SEint[5] + SEint[6] + SEint[7] + SEint[8] + SEint[9];
        SEPint[1][0] = SEint[10] + SEint[11] + SEint[12] + SEint[13] + SEint[14] +
            SEint[15] + SEint[16] + SEint[17] + SEint[18] + SEint[19];
        SEPint[2][0] = SEint[20] + SEint[21] + SEint[22] + SEint[23] + SEint[24] +
            SEint[25] + SEint[26] + SEint[27] + SEint[8] + SEint[9];
        SEPint[3][0] = SEint[28] + SEint[29] + SEint[30] + SEint[31] + SEint[32] +
            SEint[33] + SEint[34] + SEint[35];
        SEPint[4][0] = SEint[0] + SEint[1] +
            SEint[5] + SEint[6] + SEint[7] + SEint[8] + SEint[9] +
            SEint[10] + SEint[11] + SEint[13] + SEint[14];
        SEPint[5][0] = SEint[2] + SEint[3] + SEint[4] +
            SEint[8] + SEint[9] + SEint[10] +
            SEint[16] + SEint[17];
        
        //d
        var punkte = false;
        for k in 0...5
        {
            punkte = false;
            for p in 0...4
            {
                if (Double(SEPint[k][0]) < normSE[k][p])
                {
                    SEPint[k][1] = p + 1;
                    punkte = true;
                    break;
                }
            }
            if (!punkte)
            {
                SEPint[k][1] = 5;
            }
        }
        
        //e
        
        
        ProgressViewKompetenz1.progress = Float(Double(SEPint[0][1]) * 0.2);
        ProgressViewKompetenz2.progress = Float(Double(SEPint[1][1]) * 0.2);
        ProgressViewKompetenz3.progress = Float(Double(SEPint[2][1]) * 0.2);
        ProgressViewKompetenz4.progress = Float(Double(SEPint[3][1]) * 0.2);
        ProgressViewKompetenz5.progress = Float(Double(SEPint[4][1]) * 0.2);
        ProgressViewKompetenz6.progress = Float(Double(SEPint[5][1]) * 0.2);

        
    }
        
    
    
    @IBAction func ButtonImmer(_ sender: UIButton) {
        SEint[itemNr - 1] = 4;
    }

    @IBAction func ButtonMeistens(_ sender: UIButton) {
        SEint[itemNr - 1] = 3;
    }
    
    @IBAction func ButtonSelten(_ sender: UIButton) {
        SEint[itemNr - 1] = 2;
    }
    
    @IBAction func ButtonNie(_ sender: UIButton) {
        SEint[itemNr - 1] = 1;
    }
    
        
 
    
    
    

    
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let normSEtest = readNormTable();
        if (normSEtest != [[]]){
            let normSE = normSEtest;
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

