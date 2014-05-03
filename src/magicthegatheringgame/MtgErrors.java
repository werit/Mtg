/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegatheringgame;

/**
 *
 * @author msi
 */
public class MtgErrors {
    /**
     * Error used during reading xml file. 
     * Unknown property found. List of properties can be found in class Game#cardProperties.
     * @param propertyFound Name of found, unknown, property
     * @param err This parameter specifies which error occurred.
     * @return Return value is string describing this error.
     */
    public static String unknownPart(String propertyFound,Game.Errs err){
        String unknown;
        String supported;
        String whereAreSupported;
        switch(err){
            case XML_PROPERTY_UNKNOWN:
                unknown = "card property";
                supported = "properties";
                whereAreSupported = "Game.cardProperties";
                break;
            case XML_CARD_TYPE_UNKNOWN:
                unknown = "card type";
                supported = "types";
                whereAreSupported = "Game.cardTypes";
                break;
            case XML_BOOST_TIME_OF_USE_UNKNOWN:
                unknown = "boost time of use";
                supported = "time's of use";
                whereAreSupported = "Game.boostUsabil";
                break;
                default:
                    unknown = "error";
                    supported = "error";
                    whereAreSupported = "error";
                    
        }
        return "Unknown " + unknown +". Supported " + supported + " are in " + whereAreSupported + ". Found '" + propertyFound + "'";
    }
    /**
     * Error describing wrong amount of parameters.
     * @param elementName Name of XML element in which this error occurred.
     * @param hadParameters Number of parameters element has.
     * @param expectedAmntOfParam Number expected by program.
     * @return Error message.
     */
    public static String wrongAmntOfParam(String elementName,int hadParameters,int expectedAmntOfParam){
        return "Element " + elementName + " has wrong amount of parameters. Expected amount: " + expectedAmntOfParam + " but element has " + hadParameters;
    }
}
