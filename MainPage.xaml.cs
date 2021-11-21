using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace DuesseldorferSchuelerinventar
{
    [DesignTimeVisible(false)]
    public partial class MainPage : ContentPage
    {
        /********************************************************************************/
        /* Paul Koop Düsseldorfer Schülerinventar                                       */
        /* Selbsteinschätzung Normtabelle HS                                            */
        /********************************************************************************/

        //Deklaration Normtabelle N SE HS
        double[,] normSE = new double[,] {
            {21.33,25.33,29.33,33.32,37.32},
            {20.87,24.95,29.03,33.10,37.18},
            {17.93,21.37,24.80,28.23,31.67},
            {13.98,17.71,21.44,25.17,28.90},
            {15.53,18.97,22.40,25.83,29.27},
            {24.06,28.55,33.04,37.53,42.01}

         };

        //Deklaration Punkte Item SE 1..36

        string[] items = new String[] {
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

        int[] SEint = new int[]{
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

        int[,] SEPint = new int[,] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };

        //Deklaration Itemnummer
        int itemNr;

        public MainPage()
        {
            InitializeComponent();
            itemNr = Convert.ToInt32(textItemNr.Text);
        }


        private void ButtonWeiter_Clicked(object sender, EventArgs e)
        {
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
            if (itemNr > 36) { itemNr = 1; }
            textItemNr.Text = Convert.ToString(itemNr);
            textItem.Text = items[itemNr - 1];
            //c


            SEPint[0, 0] = SEint[0] + SEint[1] + SEint[2] + SEint[3] + SEint[4] +
                    SEint[5] + SEint[6] + SEint[7] + SEint[8] + SEint[9];
            SEPint[1, 0] = SEint[10] + SEint[11] + SEint[12] + SEint[13] + SEint[14] +
                    SEint[15] + SEint[16] + SEint[17] + SEint[18] + SEint[19];
            SEPint[2, 0] = SEint[20] + SEint[21] + SEint[22] + SEint[23] + SEint[24] +
                    SEint[25] + SEint[26] + SEint[27] + SEint[8] + SEint[9];
            SEPint[3, 0] = SEint[28] + SEint[29] + SEint[30] + SEint[31] + SEint[32] +
                    SEint[33] + SEint[34] + SEint[35];
            SEPint[4, 0] = SEint[0] + SEint[1] +
                    SEint[5] + SEint[6] + SEint[7] + SEint[8] + SEint[9] +
                    SEint[10] + SEint[11] + SEint[13] + SEint[14];
            SEPint[5, 0] = SEint[2] + SEint[3] + SEint[4] +
                    SEint[8] + SEint[9] + SEint[10] +
                    SEint[16] + SEint[17];
            //d
            bool punkte = false;

            for (int k = 0; k <= 5; k++)
            {
                punkte = false;
                for (int p = 0; p <= 4; p++)
                {
                    if (SEPint[k, 0] < (int)normSE[k, p])
                    {
                        SEPint[k, 1] = p + 1;
                        punkte = true;
                        p = 5;
                    }
                }
                if (!punkte)
                {
                    SEPint[k, 1] = 5;
                }
            }
            //e
            progressBarKompetenz1.Progress = (SEPint[0, 1] * 0.2);
            progressBarKompetenz2.Progress = (SEPint[1, 1] * 0.2);
            progressBarKompetenz3.Progress = (SEPint[2, 1] * 0.2);
            progressBarKompetenz4.Progress = (SEPint[3, 1] * 0.2);
            progressBarKompetenz5.Progress = (SEPint[4, 1] * 0.2);
            progressBarKompetenz6.Progress = (SEPint[5, 1] * 0.2);

        }

        private void ButtonImmer_Clicked(object sender, EventArgs e)
        {
            SEint[itemNr - 1] = 4;
        }

        private void ButtonMeistens_Clicked(object sender, EventArgs e)
        {
            SEint[itemNr - 1] = 3;
        }

        private void ButtonSelten_Clicked(object sender, EventArgs e)
        {
            SEint[itemNr - 1] = 2;
        }

        private void ButtonNie_Clicked(object sender, EventArgs e)
        {
            SEint[itemNr - 1] = 1;
        }

    }
}

