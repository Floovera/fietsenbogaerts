package be.one16.barka.klant.core.materiaal;

public class MateriaalUtil {

    protected static double calculateTotaalInclusBtwWithDiscount(double aantalUren, double uurTarief,int korting){

        double totaalInclus =  aantalUren * uurTarief * (1-(korting/100.0));
        double totaalInclusRounded = round(totaalInclus);
        return totaalInclusRounded;
    }

    protected static double calculateTotaalExclusBtw(double totaalInclusBtw, int btwPerc){
        double totaalExclus =  totaalInclusBtw / (1+(btwPerc/100.0));
        double totaalExlcusRounded = round(totaalExclus);
        return totaalExlcusRounded;
    }

    protected static double calculateBtwBedrag(double totaalInclusBtw, double totaalExlcusBtw){

        double btwBedrag =  totaalInclusBtw - totaalExlcusBtw;
        double btwBedragRounded = round(btwBedrag);
        return btwBedragRounded;
    }

    protected static double round(double amountToRound){

        double roundOff = Math.round(amountToRound * 100.0) / 100.0;
        return roundOff;
    }
}