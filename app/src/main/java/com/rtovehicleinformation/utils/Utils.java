package com.rtovehicleinformation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.rtovehicleinformation.R;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

public class Utils {
    public static String[] actorsname = new String[]{"Amitabh Bachachan", "Farhan Akhtar", "Sanjay Dutt", "Salman Khan", "Arbaaz Khan", "Ranbir R Kapoor", "John Abraham", "Arjun Kapoor", "Hritik R Roshan", "Kapil Sharma", "Emran Hashmi", "Amir Khan", "David Dhawan", "Sidharth Malhotra", "Sushant Singh Rajput", "Sonu Pankaj Sood", "Shahid Kapoor", "Neil Nitin Mukesh", "Vivek Anand Oberoi", "Prabhas Uvsr", "Abhishek Bachachan"};
    public static String[] actorsnum = new String[]{"MH02EE1100", "MH02BJ7800", "MH02CB4545", "MH46Z2727", "MH02BY2727", "MH02BT0008", "MH02CZ0300", "MH02CP2600", "MH46AD1001", "MH04FZ7740", "MH02CP8880", "MH02DZ0007", "MH02CW7668", "MH02DN1600", "MH02EC4747", "MH02DM0007", "MH02EH7000", "MH01AU2277", "MH14FM9707", "AP09CJ5677", "MH02CV2882"};
    public static String[] actressesname = new String[]{"Shraddha Kapoor", "Kajol", "Parineeti Chopra", "Alia Bhatt", "Shilpa Shetty", "Priyanka Chopra", "Bipasa Basu", "Kareena Kapoor", "Shweta Tiwari", "Jacqueline G Fernandez", "Sonakshi Sinha", "Sonam Kapoor", "Neha Saraf", "Vidya Balan", "Rani Mukherjee", "Neha Sharma", "Kangana A Ranaut", "Rekha Ganeshan", "Asin Thottumkal", "Soha Ali Khan", "Genelia D'Souza"};
    public static String[] actressesnum = new String[]{"MH04GM9660", "MH46X4001", "HR01AH0081", "MH02DW1500", "MH05CA0010", "MH02DJ9099", "MH02CZ0700", "MH02DZ4323", "MH02DJ8434", "MH02CL0200", "MH12JB0222", "MH46AL6116", "MH01BN5539", "MH03AR9577", "MH02JP9927", "MH43AT6990", "MH02BM0005", "MH02BJ4681", "MH02BZ0004", "DL8CP4545", "MH02BP5550"};
    public static int[] cautionaryImage = new int[]{R.drawable.right_hand_curve, R.drawable.left_hand_curve, R.drawable.right_hand_pin_bend, R.drawable.left_hand_pin_bend, R.drawable.cautionary5, R.drawable.left_reverse_bend, R.drawable.steep_ascent, R.drawable.steep_descent, R.drawable.cautionary9, R.drawable.cautionary10, R.drawable.narrow_road, R.drawable.road_widens, R.drawable.narrow_bridge, R.drawable.slippery_road, R.drawable.loose_gravel, R.drawable.cycle_crossing, R.drawable.pedestrian_crossing, R.drawable.school, R.drawable.men_at_work, R.drawable.cattle, R.drawable.falling_rocks, R.drawable.cautionary22, R.drawable.cross_road, R.drawable.gap_in_median, R.drawable.side_road_right, R.drawable.side_road_left, R.drawable.y_intersection, R.drawable.cautionary28, R.drawable.cautionary29, R.drawable.t_intersection, R.drawable.staggered_intersection, R.drawable.cautionary32, R.drawable.cautionary33, R.drawable.major_road, R.drawable.round_about, R.drawable.dangerous_dip, R.drawable.cautionary38, R.drawable.cautionary39, R.drawable.start_of_dual_carriageway, R.drawable.cautionary41, R.drawable.reduced_carriageway, R.drawable.cautionary43, R.drawable.two_way, R.drawable.cautionary46, R.drawable.cautionary47, R.drawable.cautionary49, R.drawable.cautionary50, R.drawable.cautionary51, R.drawable.cautionary52, R.drawable.hump_or_rough_road, R.drawable.cautionary54, R.drawable.cautionary55, R.drawable.unguarded_railway_crossing, R.drawable.cautionary57};
    public static int[] cautionaryName = new int[]{R.string.righthandcurve, R.string.lefthandcurve, R.string.righthairpinbend, R.string.lefthairpinbend, R.string.rightreversebend, R.string.leftreversebend, R.string.steepascent, R.string.steepdescent, R.string.quarysideorriverbank, R.string.overheadcable, R.string.narrowroadahead, R.string.roadwidenessahead, R.string.narrowBridge, R.string.slipperyRoad, R.string.looseGravel, R.string.cycleCrossing, R.string.pedestrianCrossing, R.string.schoolAhead, R.string.menatWork, R.string.cattle, R.string.fallingRock, R.string.ferry, R.string.crossRoad, R.string.gapinMedian, R.string.sideRoadRight, R.string.sideRoadLeft, R.string.yintersection, R.string.yintersection, R.string.yintersection, R.string.tintersection, R.string.tintersection, R.string.staggered, R.string.staggeredintersection, R.string.majorroadahead, R.string.majorroadahead, R.string.roundabout, R.string.dangerousdip, R.string.humporroughroad, R.string.barrierahead, R.string.startofduelcarriageway, R.string.endofdualcarriageway, R.string.reducedcarriageway, R.string.trafficediversion, R.string.traffifsingnals, R.string.laneclosure, R.string.runway, R.string.seriesofbends, R.string.suddensidewind, R.string.rumblestrip, R.string.speedbreaker, R.string.unguareded1, R.string.unguarded2, R.string.guarded1, R.string.guarded2};
    public static int[] celebsCarImg = new int[]{R.drawable.salman_khan_1, R.drawable.priyanka_chopra_2, R.drawable.david_dhawan_3, R.drawable.saif_ali_khan_4, R.drawable.ranbir_kapoor_5, R.drawable.rishi_kapoor_6, R.drawable.yuvraj_singh_7, R.drawable.shraddha_kapoor_8, R.drawable.abhishek_bachchan_9, R.drawable.rahul_sharma_10, R.drawable.farhan_akhtar_11, R.drawable.kangana_ranaut_12, R.drawable.kapil_sharma_13, R.drawable.emran_hashmi_14, R.drawable.shahrukh_khan_15, R.drawable.arbaaz_khan_16, R.drawable.sanjay_datt_17, R.drawable.shahid_kapoor_18, R.drawable.jacqueline_fernan_19, R.drawable.ranveer_singh_20, R.drawable.jayalalitha_21};
    public static int[] celebsName = new int[]{R.string.celeb_name1, R.string.celeb_name2, R.string.celeb_name3, R.string.celeb_name4, R.string.celeb_name5, R.string.celeb_name6, R.string.celeb_name7, R.string.celeb_name8, R.string.celeb_name9, R.string.celeb_name10, R.string.celeb_name11, R.string.celeb_name12, R.string.celeb_name13, R.string.celeb_name14, R.string.celeb_name15, R.string.celeb_name16, R.string.celeb_name17, R.string.celeb_name18, R.string.celeb_name19, R.string.celeb_name20, R.string.celeb_name21};
    public static int[] celebsNum = new int[]{R.string.celeb_no1, R.string.celeb_no2, R.string.celeb_no3, R.string.celeb_no4, R.string.celeb_no5, R.string.celeb_no6, R.string.celeb_no7, R.string.celeb_no8, R.string.celeb_no9, R.string.celeb_no10, R.string.celeb_no11, R.string.celeb_no12, R.string.celeb_no13, R.string.celeb_no14, R.string.celeb_no15, R.string.celeb_no16, R.string.celeb_no17, R.string.celeb_no18, R.string.celeb_no19, R.string.celeb_no20, R.string.celeb_no21};
    public static String[] dancersname = new String[]{"Tiger Shroff", "Allu Arjun", "Farha Khan", "Mudassar Khan", "Hema Malini", "Kunwar Amar"};
    public static String[] dancersnum = new String[]{"MH02EP0447", "AP09CQ0666", "MH02DJ6444", "MH04DR5499", "HR26AM5556", "MH01AE8398"};
    public static int[] informatoryImage = new int[]{R.drawable.public_telephone, R.drawable.filling_station, R.drawable.hospital, R.drawable.eating_place, R.drawable.light_refreshment, R.drawable.no_through_road, R.drawable.no_through_side_road, R.drawable.first_aid_post, R.drawable.parking_this_side, R.drawable.parking_both_sides, R.drawable.cycle_rickshaw_stand, R.drawable.auto_rickshaw_stand, R.drawable.taxi_stand, R.drawable.cycle_stand, R.drawable.rotary_intersection, R.drawable.advance_direction, R.drawable.reassurance, R.drawable.direction_two, R.drawable.place_identification};
    public static int[] informatoryName = new int[]{R.string.Publictelephone, R.string.Petrolpump, R.string.Hospital, R.string.Eatingplace, R.string.LightRefreshment, R.string.Nothroughroad, R.string.Nothroghsideroad, R.string.Firstaidpost, R.string.Parkthisside, R.string.Parkingbothsides, R.string.ParkinglotBikes, R.string.ParkinglotAuto, R.string.ParkinglotTaxis, R.string.ParkinglotCycles, R.string.Advanceddirectionsign, R.string.Destinationsign, R.string.Reassurancesign, R.string.Directionsign, R.string.Placeidentificationsign};
    public static int[] mandatoryImage = new int[]{R.drawable.straight_prohibited, R.drawable.no_entry, R.drawable.one_way, R.drawable.one_way_two, R.drawable.no_way_both_directions, R.drawable.all_motor_vehicles_prohibited, R.drawable.trucks_prohibited, R.drawable.cycles_prohibited, R.drawable.horn_prohibited, R.drawable.carts_prohibited, R.drawable.bullock_cart_prohibited, R.drawable.tonga_prohibited, R.drawable.hand_cart_prohibited, R.drawable.pedestrians_prohibited, R.drawable.right_turn_prohibited, R.drawable.left_turn_prohibited, R.drawable.u_turn_prohibited, R.drawable.overtaking_prohibited, R.drawable.no_parking, R.drawable.no_parking_or_stopping, R.drawable.speed_limit, R.drawable.width_limit, R.drawable.height_limit, R.drawable.trucks_prohibited, R.drawable.load_limit, R.drawable.axle_load_limit, R.drawable.buses_only, R.drawable.restriction_ands, R.drawable.cycle_ricksha_tracke, R.drawable.sound_horn, R.drawable.keep_left, R.drawable.turn_left, R.drawable.turn_right, R.drawable.ahead_or_turn_right, R.drawable.ahead_or_turn_left, R.drawable.ahead, R.drawable.sleepy_road_ahead, R.drawable.main_road_ahead, R.drawable.stop_, R.drawable.give_way};
    public static int[] mandatoryName = new int[]{R.string.straightprohibited, R.string.noentry, R.string.oneway, R.string.oneway_, R.string.nowayinbothdirections, R.string.allvehiclesprohibited, R.string.trucksprohibited, R.string.cyclesprohibited, R.string.hornsprohibited, R.string.Cartsprohibited, R.string.Bullockprohibited, R.string.Tongasprohibited, R.string.handcartsprohibited, R.string.pedestriansprohibited, R.string.rightturnprohibited, R.string.leftturnprohibited, R.string.uturnprohibited, R.string.overtakingprohibited, R.string.noParking, R.string.nostopping, R.string.speedlimit, R.string.widthlimit, R.string.heightlimit, R.string.lengthlimit, R.string.loadlimit, R.string.axleloadlimit, R.string.busesonly, R.string.restrictionendssign, R.string.compulsorycycletrack, R.string.compulsorysoundhorn, R.string.compulsorykeepleft, R.string.compulsoryturnleft, R.string.compulsoryturnright, R.string.compulsoryaheadorturnright, R.string.compulsoryaheadorturnleft, R.string.compulsoryahead, R.string.sliproadahead, R.string.mainroadahead, R.string.stop, R.string.giveway};
    public static String[] mrperfectsname = new String[]{"Mukesh Ambani", "Anil Ambani", "Ratan Tata", "Acharya Balkrishna", "Raj Kundra", "Anand Mahindra", "Naveen Jindal", "Aakash Ambani"};
    public static String[] mrperfectsnum = new String[]{"MH01CP8619", "DL4CAN5193", "MH01AL0111", "UK08AL0008", "MH05CA0010", "MH01BR1555", "DL3CBV3946", "MH01CL0123"};
    public static String[] politiciansname = new String[]{"Narendra Modi", "Arun Jaitley", "Arwind Kejriwal", "Jayalalitha", "Rahul Gandhi", "Sushma Swaraj", "Paresh Rawal", "Mulayam Singh", "Nitin Gadakari", "Swaroop Paresh Rawal", "Akhilesh Yadav", "Piyush Goyal"};
    public static String[] politiciansnum = new String[]{"DL4CCB3333", "DL10CA6666", "DL9CG9769", "TN09BE6167", "DL2CQ4067", "DL1CS4444", "UP65M4149", "UP32BG5804", "MH49AE2700", "MH02EP6798", "UP65CM4149", "DL9CZ0333"};
    public static int[] roadImage = new int[]{R.drawable.road1, R.drawable.road2, R.drawable.road3, R.drawable.road4, R.drawable.road5, R.drawable.road6, R.drawable.road7, R.drawable.road8, R.drawable.road9, R.drawable.road10, R.drawable.road11, R.drawable.road12, R.drawable.road13, R.drawable.road14, R.drawable.road17, R.drawable.road21, R.drawable.road28, R.drawable.road24, R.drawable.road29, R.drawable.road30, R.drawable.roadv1, R.drawable.roadv2, R.drawable.roadv3, R.drawable.roadv4, R.drawable.roadv5, R.drawable.roadv6, R.drawable.road7};
    public static int[] roadName = new int[]{R.string.road_1, R.string.road_2, R.string.road_3, R.string.road_4, R.string.road_5, R.string.road_6, R.string.road_7, R.string.road_8, R.string.road_9, R.string.road_10, R.string.road_11, R.string.road_12, R.string.road_13, R.string.road_14, R.string.road_15, R.string.road_16, R.string.road_17, R.string.road_18, R.string.road_19, R.string.road_20, R.string.road_21, R.string.road_22, R.string.road_23, R.string.road_24, R.string.road_25, R.string.road_26, R.string.road_27};
    public static int[] rulesImage = new int[]{R.drawable.signal_left, R.drawable.signal_right, R.drawable.signal_stop, R.drawable.signal_slowing, R.drawable.signal_overtake};
    public static int[] rulesName = new int[]{R.string.Signalforleftturn, R.string.Signalforrightturn, R.string.Signalforstopping, R.string.Signalforslowingdown, R.string.Signalforallowingovertaking};
    public static String[] singersname = new String[]{"Arijit Singh", "Sonu Nigam", "Anu Malik", "Satinder Pal Singh Sartaaj", "Arman Malik", "Farhan Javed Akhtar", "Aditya Udit Narayan Jha", "Tajinder Singh {Babbu Maan}", "Abhijit Bhattacharya", "Jagjit Singh", "Amrik Singh", "Abhijit Sawant", "Salim P Merchant", "Amit Kumar Verma", "Joseph Vijay"};
    public static String[] singersnum = new String[]{"MP04CM6578", "MH02CG0002", "MH02EP7027", "DL06CM5689", "PY01BS4040", "MH02DZ7800", "MH14FZ6000", "PB04S9244", "MH40AR0919", "DL6CH1658", "DL6CM5689", "MH02DW7576", "MH02DN0600", "CG07BE4966", "TN07BV0014"};
    public static String[] sportspersonsname = new String[]{"Sachin R. Tendulkar", "Mahendra Singh Dhoni", "Virat Kohli", "Yuvraj Singh", "Virendra Sehwag", "Saurav Ganguly", "Harbhajan Singh"};
    public static String[] sportspersonsnum = new String[]{"MH46W1011", "JH01AV1976", "HR26BW0018", "CH04H0001", "DL1CX2903", "WB02G5555", "PB080003"};
    public static int[] symbolImage = new int[]{R.drawable.straight_prohibited, R.drawable.flashing_red, R.drawable.men_at_work, R.drawable.eating_place, R.drawable.road, R.drawable.signal_slowing, R.drawable.to_stop_vehicles_behind};
    public static int[] symbolName = new int[]{R.string.mandatory, R.string.signals, R.string.cautionary, R.string.informatory, R.string.road, R.string.rules, R.string.traffifsingnals};
    public static int[] trafficLightSignals1 = new int[]{R.string.sig_tit1, R.string.sig_tit2, R.string.sig_tit3, R.string.sig_tit4, R.string.sig_tit5, R.string.sig_tit6};
    public static int[] trafficLightSignals2 = new int[]{R.string.signal1, R.string.signal2, R.string.signal3, R.string.signal4, R.string.signal5, R.string.signal6};
    public static int[] trafficLightSignalsImg = new int[]{R.drawable.stop, R.drawable.be_alert, R.drawable.go, R.drawable.steady_arrow, R.drawable.flashing_red, R.drawable.flashing_yellow};
    public static int[] trafficPoliceImage = new int[]{R.drawable.to_start_one_sided_vehicles, R.drawable.hand2, R.drawable.hand3, R.drawable.to_stop_vehicles_behind, R.drawable.to_stop_vehicles, R.drawable.to_start_vehicle_approaching_from_left, R.drawable.to_start_vehicles_coming_from_right, R.drawable.to_change_sign, R.drawable.to_start_vehicles_on_t_point, R.drawable.to_manage_vehicles_on_t_point};
    public static int[] trafficPoliceName = new int[]{R.string.Tostartonesidedvehicles, R.string.Tostopvehiclescomingfromfront, R.string.Tostopvehiclesapproachingfrombehind, R.string.Tostopvehiclesfromfrontandbehind, R.string.Tostopvehiclesfromleftandright, R.string.Tostartvehicleapproachingfromleft, R.string.Tostartvehiclescomingfromright, R.string.Tochangesign, R.string.TogiveVIPhealth, R.string.TomanagevehiclesonT_Point};


    public static boolean checkConnectivity(Context lCon, final boolean show) {
        if (isNetworkConnected(lCon)) {
            return true;
        }
        if (show) {
            Toast.makeText(lCon, "Data/Wifi Not Available", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public static boolean isNetworkConnected(final Context lCon) {
        final ConnectivityManager cm = (ConnectivityManager) lCon.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
