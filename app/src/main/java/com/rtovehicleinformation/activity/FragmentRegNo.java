package com.rtovehicleinformation.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfWriter;
import com.rtovehicleinformation.R;
import com.rtovehicleinformation.utils.HeaderFooterPageEvent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentRegNo extends Fragment {
    private static final String EXTRA_TEXT = "text";
    private static String TAG = "FragmentRegNo";
    private static int count = -1;
    ArrayList<String> arrayList;
    String blacklistMsg;
    TextView blacklistMsgTV;
    String chasisNo;
    TextView chasisNoTV;
    int currentDay;
    int currentMonth;
    int currentYear;
    String engineNo;
    TextView engineNoTV;
    String fitUpto;
    TextView fitUptoTV;
    String formatted;
    String fuelNorms;
    TextView fuelNormsTV;
    String fuelType;
    TextView fuelTypeTV;
    Button getData;
    EditText input1;
    EditText input2;
    EditText input3;
    EditText input4;
    String insUpto;
    TextView insUptoTV;
    String jsonStr;
    String maker;
    TextView makerTV;
    String owner;
    TextView ownerTV;
    ProgressDialog pd;
    String reason;
    String regDate;
    TextView regDateTV;
    String regNo;
    TextView regNoTV;
    String result;
    String resultTwo;
    String rto;
    TextView rtoTV;
    ScrollView scrollView;
    ArrayList<String> shortedarrayList;
    String state;
    TextView stateTV;
    String status;
    String str2;
    String trim;
    String veh_class;
    TextView veh_classTV;
    String vehicleAge;
    TextView vehicleAgeTV;
    Button PrintData;
    String num1;
    String num2;
    String num3;
    String num4;
//    @BindView(R.id.iv_back)
//    ImageView ivback;

    String str;
    String substring;

    public FragmentRegNo() {
        this.arrayList = new ArrayList<String>();
        this.shortedarrayList = new ArrayList<String>();
        this.str2 = "";
    }

    public static Fragment getInstance(String num1, String num2, String num3, String num4) {
        Bundle bundle = new Bundle();
        bundle.putString("first", num1);
        bundle.putString("second", num2);
        bundle.putString("third", num3);
        bundle.putString("fourth", num4);
        FragmentRegNo fragmentRegNo = new FragmentRegNo();
        fragmentRegNo.setArguments(bundle);
        return fragmentRegNo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num1 = getArguments().getString("first");
        num2 = getArguments().getString("second");
        num3 = getArguments().getString("third");
        num4 = getArguments().getString("fourth");

    }

    private boolean checkIfAlreadyhavePermission() {
        return ContextCompat.checkSelfPermission(this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    private void openScreenshot(final File file) {
        final Uri uriForFile = FileProvider.getUriForFile(this.getActivity(), "com.abcd.rtovehicalinformation.provider", file);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Share vehicle details");
        builder.setMessage("Your  Vehicle Details have been saved to PDF in \"ViewVehicleDetails\" folder.").setCancelable(true).setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                final Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setDataAndType(uriForFile, "application/pdf");
                FragmentRegNo.this.startActivity(intent);
            }
        }).setNegativeButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                final Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.SUBJECT", FragmentRegNo.this.getString(R.string.app_name));
                final StringBuilder sb = new StringBuilder();
                sb.append("View All India Vehicle details - Get \n App @ https://play.google.com/store/apps/details?id=");
                sb.append(FragmentRegNo.this.getActivity().getPackageName());
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.setType("image/*");
                FragmentRegNo.this.startActivity(Intent.createChooser(intent, "Share"));
            }
        });
        builder.create().show();
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
    }

    public void createandDisplayPdf(final String s) {
        (this.pd = new ProgressDialog(this.getActivity())).setMessage("Printing...");
        this.pd.show();
        final Document document = new Document(PageSize.A4, 20.0f, 20.0f, 50.0f, 25.0f);
        try {
            try {
                "mounted".equals(Environment.getExternalStorageState());
                final StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append(File.separator);
                sb.append("ViewVehicleDetails");
                final File file = new File(sb.toString());
                if (!file.exists()) {
                    file.mkdir();
                }
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(this.result);
                sb2.append(".pdf");
                final File file2 = new File(file, sb2.toString());
                PdfWriter.getInstance(document, new FileOutputStream(file2)).setPageEvent(new HeaderFooterPageEvent());
                document.open();
                final Paragraph paragraph = new Paragraph(s);
                final Font font = new Font(Font.FontFamily.COURIER);
                paragraph.setAlignment(0);
                paragraph.setFont(font);
                document.addTitle("VEHICLE DETAILS");
                document.add(paragraph);
                document.close();
                new CountDownTimer(500L, 500L) {
                    public void onFinish() {
                        FragmentRegNo.this.openScreenshot(file2);
                        FragmentRegNo.this.pd.dismiss();
                    }

                    public void onTick(final long n) {
                    }
                }.start();
            } finally {
            }
        } catch (IOException ex) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("ioException:");
            sb3.append(ex);
        } catch (DocumentException ex2) {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append("DocumentException:");
            sb4.append(ex2);
        }
        document.close();
    }

    public boolean isConnected(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            final NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            final NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            return (networkInfo2 != null && networkInfo2.isConnectedOrConnecting()) || (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
        return false;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.freg_layout_reg, viewGroup, false);
        final String[] split = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()).split("-");
        final String s = split[0];
        final String s2 = split[1];
        final String s3 = split[2];
        this.currentYear = Integer.parseInt(s);
        this.currentMonth = Integer.parseInt(s2);
        this.currentDay = Integer.parseInt(s3);
        this.veh_classTV = inflate.findViewById(R.id.modelName);
        this.engineNoTV = inflate.findViewById(R.id.engineNo);
        this.stateTV = inflate.findViewById(R.id.state);
        this.regNoTV = inflate.findViewById(R.id.regNumber);
        this.makerTV = inflate.findViewById(R.id.modelName);
        this.insUptoTV = inflate.findViewById(R.id.insUpto);
        this.rtoTV = inflate.findViewById(R.id.city);
        this.fitUptoTV = inflate.findViewById(R.id.fitness);
        this.ownerTV = inflate.findViewById(R.id.ownerName);
        this.fuelNormsTV = inflate.findViewById(R.id.fNorms);
        this.fuelTypeTV = inflate.findViewById(R.id.fuelType);
        this.chasisNoTV = inflate.findViewById(R.id.chasisNo);
        this.vehicleAgeTV = inflate.findViewById(R.id.vehicleAge);
        this.regDateTV = inflate.findViewById(R.id.regDate);
        this.input1 = inflate.findViewById(R.id.input1);
        this.input2 = inflate.findViewById(R.id.input2);
        this.input3 = inflate.findViewById(R.id.input3);
        this.input4 = inflate.findViewById(R.id.input4);
        this.scrollView = inflate.findViewById(R.id.scrollView);
        loadAd();
        scrollView.setDrawingCacheEnabled(true);


        if (num1 != null && num2 != null && num3 != null && num4 != null) {
            input1.setText(num1);
            input2.setText(num2);
            input3.setText(num3);
            input4.setText(num4);

            /*input1.setText(num1.substring(0, 2));
            input2.setText(num1.substring(2, 4));
            input3.setText(num1.substring(4, 6));
            input4.setText(num2);*/

            input1.setTextColor(getResources().getColor(R.color.colorAccent));
            input2.setTextColor(getResources().getColor(R.color.colorAccent));
            input3.setTextColor(getResources().getColor(R.color.colorAccent));
            input4.setTextColor(getResources().getColor(R.color.colorAccent));
            input1.setEnabled(false);
            input2.setEnabled(false);
            input3.setEnabled(false);
            input4.setEnabled(false);
        }

        this.input1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }

            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }

            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                if (FragmentRegNo.this.input1.getText().toString().length() == 2) {
                    FragmentRegNo.this.input2.setText("");
                    FragmentRegNo.this.input2.requestFocus();
                }
            }
        });
        this.input2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }

            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }

            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                if (FragmentRegNo.this.input2.getText().toString().length() == 2) {
                    FragmentRegNo.this.input3.setText("");
                    FragmentRegNo.this.input3.requestFocus();
                }
            }
        });
        this.input3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }

            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }

            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                if (FragmentRegNo.this.input3.getText().toString().length() == 2) {
                    FragmentRegNo.this.input4.setText("");
                    FragmentRegNo.this.input4.requestFocus();
                }
            }
        });

        (this.getData = inflate.findViewById(R.id.getData)).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                FragmentRegNo.this.ownerTV.setText("");
                FragmentRegNo.this.regNoTV.setText("");
                FragmentRegNo.this.regDateTV.setText("");
                FragmentRegNo.this.makerTV.setText("");
                FragmentRegNo.this.vehicleAgeTV.setText("");
                FragmentRegNo.this.fuelTypeTV.setText("");
                FragmentRegNo.this.chasisNoTV.setText("");
                FragmentRegNo.this.engineNoTV.setText("");
                FragmentRegNo.this.insUptoTV.setText("");
                FragmentRegNo.this.fitUptoTV.setText("");
                FragmentRegNo.this.fuelNormsTV.setText("");
                FragmentRegNo.this.rtoTV.setText("");
                FragmentRegNo.this.stateTV.setText("");
                ((InputMethodManager) FragmentRegNo.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(FragmentRegNo.this.input4.getWindowToken(), 0);
                FragmentRegNo.this.input4.clearFocus();
                if (!FragmentRegNo.this.isConnected(FragmentRegNo.this.getActivity())) {
                    Toast.makeText(FragmentRegNo.this.getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String string = FragmentRegNo.this.input1.getText().toString();
                final String string2 = FragmentRegNo.this.input2.getText().toString();
                final String string3 = FragmentRegNo.this.input3.getText().toString();
                final String string4 = FragmentRegNo.this.input4.getText().toString();
                final StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(string2);
                sb.append(string3);
                sb.append(string4);
                Log.e("TAG", "Number" + sb.toString());
                result = sb.toString();
                final FragmentRegNo this$2 = FragmentRegNo.this;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("&reg2=");
                sb2.append(string4);
                this$2.resultTwo = sb2.toString();

                if (TextUtils.isEmpty(string)) {
                    FragmentRegNo.this.input1.setError("This should not be empty");
                    FragmentRegNo.this.input1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string2)) {
                    FragmentRegNo.this.input2.setError("This should not be empty");
                    FragmentRegNo.this.input2.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string3)) {
                    FragmentRegNo.this.input3.setError("This should not be empty");
                    FragmentRegNo.this.input3.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string4)) {
                    FragmentRegNo.this.input4.setError("This should not be empty");
                    FragmentRegNo.this.input4.requestFocus();
                    return;
                }

//                FragmentRegNo.this.arrayList.clear();
//                FragmentRegNo.this.shortedarrayList.clear();
                /*if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    String obj = input1.getText().toString();
                    String obj2 = input2.getText().toString() + input3.getText().toString();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(obj);
                    stringBuilder.append(obj2);
                    reg_no1 = stringBuilder.toString();
                    reg_no2 = input4.getText().toString();

                    new SendRequest().execute();
                }*/
                String obj = input1.getText().toString();
                String obj2 = input2.getText().toString() + input3.getText().toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(obj);
                stringBuilder.append(obj2);
                reg_no1 = stringBuilder.toString();
                reg_no2 = input4.getText().toString();

                new SendRequest().execute();
            }
        });
        PrintData = inflate.findViewById(R.id.print);
        PrintData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printData();
            }
        });
        return inflate;
    }

    private void printData() {
        if (shortedarrayList != null) {
            if (Build.VERSION.SDK_INT > 22) {
                if (!FragmentRegNo.this.checkIfAlreadyhavePermission()) {
                    FragmentRegNo.this.requestForSpecificPermission();
                    return;
                }
                final String string = FragmentRegNo.this.input1.getText().toString();
                final String string2 = FragmentRegNo.this.input2.getText().toString();
                final String string3 = FragmentRegNo.this.input3.getText().toString();
                final String string4 = FragmentRegNo.this.input4.getText().toString();
                final FragmentRegNo this$0 = FragmentRegNo.this;
                final StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(string2);
                sb.append(string3);
                sb.append(string4);
                this$0.result = sb.toString();
                final FragmentRegNo this$2 = FragmentRegNo.this;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("&reg2=");
                sb2.append(string4);
                this$2.resultTwo = sb2.toString();
                if (TextUtils.isEmpty(string)) {
                    FragmentRegNo.this.input1.setError("This should not be empty");
                    FragmentRegNo.this.input1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string2)) {
                    FragmentRegNo.this.input2.setError("This should not be empty");
                    FragmentRegNo.this.input2.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string3)) {
                    FragmentRegNo.this.input3.setError("This should not be empty");
                    FragmentRegNo.this.input3.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(string4)) {
                    FragmentRegNo.this.input4.setError("This should not be empty");
                    FragmentRegNo.this.input4.requestFocus();
                    return;
                }
                final FragmentRegNo this$3 = FragmentRegNo.this;
                final StringBuilder sb3 = new StringBuilder();
                sb3.append("Owner Name: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(1));
                sb3.append("\nRegistration No: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(3));
                sb3.append("\nRegistration Date: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(5));
                sb3.append("\nModel Name: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(15));
                sb3.append("\nFuel Type: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(13));
                sb3.append("\nChasis Number: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(7));
                sb3.append("\nEngine Number: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(9));
                sb3.append("\nInsurance Upto: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(19));
                sb3.append("\nFitness Upto: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(17));
                sb3.append("\nFuel Norms: ");
                sb3.append(FragmentRegNo.this.shortedarrayList.get(21));
                sb3.append("\nCity: ");
                sb3.append(FragmentRegNo.this.trim);
                sb3.append("\nState: ");
                sb3.append(FragmentRegNo.this.trim);
                sb3.append("\n\nGet All Inidia Vehicle details using ");
                sb3.append(FragmentRegNo.this.getActivity().getResources().getString(R.string.app_name));
                sb3.append(" Android App get app @ \n https://play.google.com/store/apps/details?id=");
                sb3.append(FragmentRegNo.this.getActivity().getPackageName());
                this$3.createandDisplayPdf(sb3.toString());
                FragmentRegNo.this.scrollView.setDrawingCacheEnabled(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    //    public class SendRequest extends AsyncTask<String, Void, String> {
//        String vehNo = FragmentRegNo.this.result;
//
//        protected void onPreExecute() {
//            FragmentRegNo.this.pd = new ProgressDialog(FragmentRegNo.this.getActivity());
//            FragmentRegNo.this.pd.setMessage("loading");
//            FragmentRegNo.this.pd.show();
//        }
//
//        protected String doInBackground(String... strArr) {
//            Matcher matcher = Pattern.compile("(\\w{2})(\\d{2})(\\D*?)(\\d{1,4})").matcher(FragmentRegNo.this.result);
//            if (matcher.matches()) {
//                FragmentRegNo.this.str2 = matcher.replaceFirst("$1$2$3-$4");
//                strArr = FragmentRegNo.this.str2.split("-");
//                String str = strArr[0];
//                String str2 = strArr[1];
//                Log.e("TAG","Str"+str);
//                Log.e("TAG","str2"+str2);
//                try {
//                    Connection.Response execute = Jsoup.connect("https://vahan.nic.in/nrservices/faces/user/searchstatus.xhtml").validateTLSCertificates(false).followRedirects(true).ignoreHttpErrors(true).method(Connection.Method.GET).execute();
//                    if (execute.statusCode() <= 500) {
//                        final Map cookies = ((Connection.Base) execute).cookies();
//                        final org.jsoup.nodes.Document parse = Jsoup.parse(execute.body());
//                        org.jsoup.nodes.Element element;
//                        if ((element = parse.getElementsByAttributeValue("name", "javax.faces.ViewState").first()) == null) {
//                            element = parse.getElementById("j_id1:javax.faces.ViewState:0");
//                        }
//                        final String attr = element.attr("value");
//                        FragmentRegNo.this.str2 = Jsoup.parse(execute.body()).getElementsByAttributeValueStarting("id", "form_rcdl:j_idt").select("button").get(0).attr("id").trim();
//                        FragmentRegNo.this.str2 = Jsoup.connect("https://vahan.nic.in/nrservices/faces/user/searchstatus.xhtml").validateTLSCertificates(false).followRedirects(true).method(Connection.Method.POST).cookies(cookies).referrer("https://vahan.nic.in/nrservices/faces/user/searchstatus.xhtml").header("Content-Type", "application/x-www-form-urlencoded").header("Host", "vahan.nic.in").header("Accept", "application/xml, text/xml, */*; q=0.01").header("Accept-Language", "en-US,en;q=0.5").header("Accept-Encoding", "gzip, deflate, br").header("X-Requested-With", "XMLHttpRequest").header("Faces-Request", "partial/ajax").header("Origin", "https://vahan.nic.in").userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Safari/605.1.15").data("javax.faces.partial.ajax", PdfBoolean.TRUE).data("javax.faces.source", FragmentRegNo.this.str2).data("javax.faces.partial.execute", "@all").data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl").data(FragmentRegNo.this.str2, FragmentRegNo.this.str2).data("form_rcdl", "form_rcdl").data("form_rcdl:tf_reg_no1", str).data("form_rcdl:tf_reg_no2", str2).data("javax.faces.ViewState", attr).execute().body();
//                        StringBuilder stringBuilder = new StringBuilder();
//                        stringBuilder.append("str2 ");
//                        stringBuilder.append(FragmentRegNo.this.str2);
//                        if (FragmentRegNo.this.str2.contains("Registration No. does not exist!!!")) {
//                            FragmentRegNo.this.shortedarrayList.add(0, "No Record(s) Found");
//                        } else {
//                            StringBuilder stringBuilder2 = new StringBuilder();
//                            stringBuilder2.append("<!DOCTYPE html><html><body>");
//                            stringBuilder2.append(FragmentRegNo.this.str2, FragmentRegNo.this.str2.indexOf("<table"), FragmentRegNo.this.str2.lastIndexOf("</table>"));
//                            stringBuilder2.append("</body></html>");
//                            final org.jsoup.nodes.Document parse2 = Jsoup.parse(stringBuilder2.toString());
//                            int indexOf = FragmentRegNo.this.str2.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize\">") + 62;
//                            FragmentRegNo.this.trim = FragmentRegNo.this.str2.substring(indexOf, FragmentRegNo.this.str2.indexOf("</div>", indexOf)).replaceAll("Registering Authority:", "").trim();
//                            final org.jsoup.nodes.Element first2 = parse2.select("table").first();
//                            if (first2 != null) {
//                                Elements select = first2.select(HtmlTags.TR);
//                                for (indexOf = 0; indexOf < select.size(); indexOf++) {
//                                    final Elements select2 = select.get(indexOf).select("td");
//                                    int i = 0;
//                                    while (i < select2.size()) {
//                                        final String text = select2.get(i).text();
//                                        if (text.trim().equals("Owner Name:") || FragmentRegNo.count == i) {
//                                            if (FragmentRegNo.count == -1) {
//                                                FragmentRegNo.count = i + 1;
//                                            } else {
//                                                FragmentRegNo.count = -1;
//                                            }
//                                            FragmentRegNo.this.shortedarrayList.add(text);
//                                        } else {
//                                            FragmentRegNo.this.arrayList.add(text);
//                                        }
//                                        i++;
//                                    }
//                                }
//                                FragmentRegNo.this.shortedarrayList.addAll(FragmentRegNo.this.arrayList);
//                            } else {
//                                FragmentRegNo.this.shortedarrayList.add(0, "No Record(s) Found");
//                            }
//                        }
//                    } else {
//                        FragmentRegNo.this.shortedarrayList.add(0, "500 Server Error");
//                    }
//                } catch (Exception unused) {
//                    Log.e("TAG", "Error Find" + unused.getMessage());
//                    FragmentRegNo.this.shortedarrayList.add(0, "Server Error");
//                }
//                return null;
//            }
//            FragmentRegNo.this.shortedarrayList.add(0, "No Record(s) Found");
//            return null;
//        }
//
//        protected void onPostExecute(String str) {
//            FragmentRegNo.this.pd.dismiss();
//            if (FragmentRegNo.this.shortedarrayList.get(0).equals("No Record(s) Found")) {
//                PrintData.setVisibility(View.GONE);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(FragmentRegNo.this.getActivity());
//                builder.setIcon(R.mipmap.ic_launcher);
//                builder.setTitle("Oops!");
//                final StringBuilder sb = new StringBuilder();
//                sb.append("No records found for ");
//                sb.append(this.vehNo);
//                sb.append("try another number");
//                builder.setMessage(sb.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialogInterface, final int n) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder.create().show();
//                return;
//            }
//            if (FragmentRegNo.this.shortedarrayList.get(0).equals("500 Server Error")) {
//                PrintData.setVisibility(View.GONE);
//                final AlertDialog.Builder builder2 = new AlertDialog.Builder(FragmentRegNo.this.getActivity());
//                builder2.setIcon(R.mipmap.ic_launcher);
//                builder2.setTitle("Error!");
//                final StringBuilder sb2 = new StringBuilder();
//                sb2.append("We got 500 server error while searching for ");
//                sb2.append(this.vehNo);
//                sb2.append(" please try again or SEARCH VEHICLE BY SMS option in menu.");
//                builder2.setMessage(sb2.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialogInterface, final int n) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder2.create().show();
//                return;
//            }
//            if (FragmentRegNo.this.shortedarrayList.get(0).equals("Server Error")) {
//                PrintData.setVisibility(View.GONE);
//                final AlertDialog.Builder builder3 = new AlertDialog.Builder(FragmentRegNo.this.getActivity());
//                builder3.setIcon(R.mipmap.ic_launcher);
//                builder3.setTitle("Error!");
//                final StringBuilder sb3 = new StringBuilder();
//                sb3.append("We got server error while searching for ");
//                sb3.append(this.vehNo);
//                sb3.append(" please try again or SEARCH VEHICLE BY SMS option in menu.");
//                builder3.setMessage(sb3.toString()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialogInterface, final int n) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder3.create().show();
//                return;
//            }
//            PrintData.setVisibility(View.VISIBLE);
//            FragmentRegNo.this.ownerTV.setText(FragmentRegNo.this.shortedarrayList.get(1));
//            FragmentRegNo.this.regNoTV.setText(FragmentRegNo.this.shortedarrayList.get(3));
//            FragmentRegNo.this.regDateTV.setText(FragmentRegNo.this.shortedarrayList.get(5));
//            FragmentRegNo.this.makerTV.setText(FragmentRegNo.this.shortedarrayList.get(15));
//            FragmentRegNo.this.vehicleAgeTV.setText("");
//            FragmentRegNo.this.fuelTypeTV.setText(FragmentRegNo.this.shortedarrayList.get(13));
//            FragmentRegNo.this.chasisNoTV.setText(FragmentRegNo.this.shortedarrayList.get(7));
//            FragmentRegNo.this.engineNoTV.setText(FragmentRegNo.this.shortedarrayList.get(9));
//            FragmentRegNo.this.insUptoTV.setText(FragmentRegNo.this.shortedarrayList.get(19));
//            FragmentRegNo.this.fitUptoTV.setText(FragmentRegNo.this.shortedarrayList.get(17));
//            FragmentRegNo.this.fuelNormsTV.setText(FragmentRegNo.this.shortedarrayList.get(21));
//            FragmentRegNo.this.rtoTV.setText(FragmentRegNo.this.trim);
//            FragmentRegNo.this.stateTV.setText(FragmentRegNo.this.trim);
//        }
//    }
    String reg_no1;
    String reg_no2;
    String vehicle_num_full;


    private void GetNumber(){

    }
    public class SendRequest extends AsyncTask<String, Void, ArrayList<String>> {
        public void onPreExecute() {
        }

        public SendRequest() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(reg_no1);
            stringBuilder.append(reg_no2);
            vehicle_num_full = stringBuilder.toString();
            Log.e("TAG", "Number Found" + stringBuilder.toString());
        }

        public ArrayList<String> doInBackground(String... strArr) {
            String str = "form_rcdl";
            String str2 = "id";
            String str3 = "javax.faces.ViewState";
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            try {
                Connection.Response execute = Jsoup.connect("https://vahan.nic.in").validateTLSCertificates(false).followRedirects(true).ignoreHttpErrors(true).method(Connection.Method.GET).execute();
                Log.e("TAG", "Exe" + execute.statusMessage());
                if (execute.statusCode() <= 500) {
                    Map cookies = execute.cookies();
                    org.jsoup.nodes.Document parse = Jsoup.parse(execute.body());
                    Element first = parse.getElementsByAttributeValue(AppMeasurementSdk.ConditionalUserProperty.NAME, str3).first();
                    if (first == null) {
                        first = parse.getElementById("j_id1:javax.faces.ViewState:0");
                        Log.e("TAG", "first");
                    }
                    String attr = first.attr("value");
                    str2 = Jsoup.parse(execute.body()).getElementsByAttributeValueStarting(str2, "form_rcdl:j_idt").select("button").get(0).attr(str2).trim();
                    str = Jsoup.connect("https://vahan.nic.in/nrservices/faces/user/searchstatus.xhtml").validateTLSCertificates(false).followRedirects(true).method(Connection.Method.POST).cookies(cookies).referrer("https://vahan.nic.in/nrservices/faces/user/searchstatus.xhtml").header("Content-Type", "application/x-www-form-urlencoded").header("Host", "vahan.nic.in").header("Accept", "application/xml, text/xml, */*; q=0.01").header("Accept-Language", "en-US,en;q=0.5").header("Accept-Encoding", "gzip, deflate, br").header("X-Requested-With", "XMLHttpRequest").header("Faces-Request", "partial/ajax").header("Origin", "https://vahan.nic.in").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36").data("javax.faces.partial.ajax", PdfBoolean.TRUE).data("javax.faces.source", str2).data("javax.faces.partial.execute", "@all").data("javax.faces.partial.render", "form_rcdl:pnl_show form_rcdl:pg_show form_rcdl:rcdl_pnl").data(str2, str2).data(str, str).data("form_rcdl:tf_reg_no1", reg_no1).data("form_rcdl:tf_reg_no2", reg_no2).data(str3, attr).execute().body();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("str2 ");
                    stringBuilder.append(str);
                    Log.v("TAG", stringBuilder.toString());
                    str3 = "No Record(s) Found";
                    if (str.contains("Registration No. does not exist!!!")) {
                        arrayList2.add(0, str3);
                    } else {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("<!DOCTYPE html><html><body>");
                        stringBuilder2.append(str.substring(str.indexOf("<table"), str.lastIndexOf("</table>")));
                        stringBuilder2.append("</body></html>");
                        org.jsoup.nodes.Document parse2 = Jsoup.parse(stringBuilder2.toString());
                        int indexOf = str.indexOf("<div class=\"font-bold top-space bottom-space text-capitalize\">") + 62;
                        rto = str.substring(indexOf, str.indexOf("</div>", indexOf)).replaceAll("Registering Authority:", "").trim();
                        Element first2 = parse2.select(HtmlTags.TABLE).first();
                        if (first2 != null) {
                            Elements select = first2.select(HtmlTags.TR);
                            indexOf = -1;
                            for (int i = 0; i < select.size(); i++) {
                                Elements select2 = select.get(i).select(HtmlTags.TD);
                                int i2 = 0;
                                while (i2 < select2.size()) {
                                    attr = select2.get(i2).text();
                                    if (!(attr.trim().equals("Owner Name:") || indexOf == i2)) {
                                        arrayList.add(attr);
                                    }
                                    indexOf = indexOf == -1 ? i2 + 1 : -1;
                                    arrayList2.add(attr);
                                    i2++;
                                }
                            }
                            arrayList2.addAll(arrayList);
                        } else {
                            arrayList2.add(0, str3);
                        }
                    }
                } else {
                    Log.e("TAG", "500 Server Error");
                    arrayList2.add(0, "500 Server Error");
                }
            } catch (Exception unused) {
                Log.e("TAG", "Server Error" + unused.getMessage());
                arrayList2.add(0, "Server Error");
            }
            return arrayList2;
        }

        public void onPostExecute(ArrayList<String> arrayList) {
            onSearchResponse(arrayList);
        }
    }

    private void onSearchResponse(ArrayList<String> arrayList) {
        if (arrayList.get(0).equals("No Record(s) Found")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No records found for ");
            stringBuilder.append(this.vehicle_num_full);
            stringBuilder.append(" try another number");
            Log.e("TAG", "Opps");
        } else if (arrayList.get(0).equals("500 Server Error") || arrayList.get(0).equals("Server Error")) {
            Log.e("TAG", "Something went wrong Please Try Again!");
        } else {
            Log.e("TAG", "ArrayList" + arrayList.get(1));
        }
    }


    private InterstitialAd mInterstitialAd;

    private void loadAd() {
        mInterstitialAd = new InterstitialAd(this.getActivity());
        mInterstitialAd.setAdUnitId(this.getActivity().getResources().getString(R.string.interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                new SendRequest().execute();
                requestNewInterstitial();
            }
        });
    }

    private void requestNewInterstitial() {
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
