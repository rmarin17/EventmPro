package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import unicauca.movil.eventmpro.databinding.ActivityCargaDatosBinding;
import unicauca.movil.eventmpro.db.BeaconsDao;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Beacons;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.models.Ubicacion;
import unicauca.movil.eventmpro.util.L;

public class CargaDatos extends AppCompatActivity implements DialogInterface.OnClickListener {


    private static final int READ_REQUEST_CODE = 42;

    Uri archivo;
    ActivityCargaDatosBinding binding;
    Ponente p;
    Dias d;
    Beacons b;
    Conections c;
    Evento e;
    Ubicacion u;
    PonenteDao pdao;
    BeaconsDao bdao;
    ConectionsDao cdao;
    DiasDao ddao;
    EventoDao edao;
    UbicacionDao udao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_carga_datos);

        binding.setHandler(this);

        p = new Ponente();
        d = new Dias();
        b = new Beacons();
        u = new Ubicacion();
        e = new Evento();
        c = new Conections();
        pdao = new PonenteDao(this);
        bdao = new BeaconsDao(this);
        cdao = new ConectionsDao(this);
        ddao = new DiasDao(this);
        edao = new EventoDao(this);
        udao = new UbicacionDao(this);

        performFileSearch();
    }

    public void readXmlPullParser() {
        XmlPullParserFactory factory;
        InputStream fis = null;
        String nombre = null;
        String apellido = null;
        String empresa = null;
        String tipo = null;
        try {
            StringBuilder sb = new StringBuilder();
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            InputStream inputStream = getContentResolver().openInputStream(archivo);

            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    String condi = parser.getName();

                    switch (condi){

                        //region Ponentes
                        case "idp":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idp = readText(parser);
                            p.setIdp(Integer.parseInt(idp));
                            break;

                        case "nombre":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            nombre = readText(parser);
                            p.setNombre(nombre);
                            break;

                        case "apellidos":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            apellido = readText(parser);
                            p.setApellidos(apellido);
                            break;

                        case "empresa":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            empresa = readText(parser);
                            p.setEmpresa(empresa);
                            break;

                        case "tipo":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            tipo = readText(parser);
                            p.setTipo(tipo);
                            break;

                        case "imagen":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String imagen = readText(parser);
                            p.setImagen(imagen);
                            break;

                        case "estudios":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String estudio = readText(parser);
                            p.setEstudios(estudio);
                            break;

                        case "experiencia":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String experiencia = readText(parser);
                            p.setExperiencia(experiencia);
                            break;

                        case "formacioninternacional":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String forma = readText(parser);
                            p.setFormacioninternacional(forma);
                            break;

                        case "habilidad":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String habilidad = readText(parser);
                            p.setHabilidad(habilidad);
                            pdao.insert(p);
                            break;
                        //endregion

                        //region Dias
                        case "idh":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idh = readText(parser);
                            d.setIdh(Integer.parseInt(idh));
                            //p.setImagen(img);
                            break;

                        case "idd":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idd = readText(parser);
                            d.setIdd(Integer.parseInt(idd));
                            //p.setImagen(img);

                            break;

                        case "ido":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String ido = readText(parser);
                            d.setIdo(Integer.parseInt(ido));
                            //p.setImagen(img);

                            break;

                        case "hora":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String hora = readText(parser);
                            d.setHora(hora);
                            //p.setImagen(img);
                            break;

                        case "evento":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String evento = readText(parser);
                            d.setEvento(evento);
                            //p.setImagen(img);
                            break;

                        case "titulo":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String titulo = readText(parser);
                            d.setTitulo(titulo);
                            //p.setImagen(img);
                            break;

                        case "conferencista":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String confe = readText(parser);
                            d.setConferencista(confe);
                            //p.setImagen(img);
                            break;

                        case "empresadias":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String empd = readText(parser);
                            d.setEmpresadias(empd);
                            //p.setImagen(img);
                            break;

                        case "lugar":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String lugar = readText(parser);
                            d.setLugar(lugar);
                            //p.setImagen(img);
                            ddao.insert(d);
                            break;
                        //endregion

                        //region Evento
                        case "ide":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String ide = readText(parser);
                            e.setIde(Integer.parseInt(ide));
                            break;

                        case "eventonombre":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String en = readText(parser);
                            e.setEventonombre(en);
                            break;

                        case "eventoimg":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String img = readText(parser);
                            e.setEventoimg(img);
                            break;

                        case "numerodias":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String nd = readText(parser);
                            e.setNumerodias(Integer.parseInt(nd));
                            break;

                        case "objetivo":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String obj = readText(parser);
                            e.setObjetivo(obj);
                            break;

                        case "lugarevento":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String el = readText(parser);
                            e.setLugarevento(el);
                            break;

                        case "descripcion":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String des = readText(parser);
                            e.setDescripcion(des);
                            break;

                        case "fecha":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String fecha = readText(parser);
                            e.setFecha(fecha);
                            edao.insert(e);
                            break;
                        //endregion

                        //region Ubicacion
                        case "idu":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idu = readText(parser);
                            u.setIdu(Integer.parseInt(idu));
                            break;

                        case "tituloubicacion":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String titu = readText(parser);
                            u.setTituloubicacion(titu);
                            break;

                        case "lat":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String lat = readText(parser);
                            u.setLat(Double.parseDouble(lat));
                            break;

                        case "lng":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String lng = readText(parser);
                            u.setLng(Double.parseDouble(lng));
                            udao.insert(u);
                            break;
                        //endregion

                        //region Beacons
                        case "idb":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idb = readText(parser);
                            b.setIdb(Integer.parseInt(idb));
                            break;

                        case "btitulo":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String uuid = readText(parser);
                            b.setBtitulo(uuid);
                            break;

                        case "major":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String major = readText(parser);
                            b.setMajor(Integer.parseInt(major));
                            break;

                        case "minor":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String minor = readText(parser);
                            b.setMinor(Integer.parseInt(minor));
                            break;

                        case "blat":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String blat = readText(parser);
                            b.setBlat(Double.parseDouble(blat));
                            break;

                        case "blng":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String blng = readText(parser);
                            b.setBlng(Double.parseDouble(blng));
                            bdao.insert(b);
                            break;
                        //endregion

                        //region Conections
                        case "idc":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String idc = readText(parser);
                            c.setIdc(Integer.parseInt(idc));
                            break;

                        case "cdias":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String cdias = readText(parser);
                            c.setDias(cdias);
                            break;

                        case "cponentes":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String cp = readText(parser);
                            c.setPonentes(cp);
                            break;

                        case "cubicacion":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String cu = readText(parser);
                            c.setUbicacion(cu);
                            break;

                        case "cbeacons":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String cb = readText(parser);
                            c.setBeacons(cb);
                            break;

                        case "cevento":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String ce = readText(parser);
                            c.setEvento(ce);
                            cdao.insert(c);
                            break;
                        //endregion
                    }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Toast.makeText(this, "ARCHIVO: " +uri.toString(), Toast.LENGTH_LONG).show();
                archivo = uri;

            }

        }

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            Intent inten = new Intent(this, Principal.class);
            startActivity(inten);
            finish();
        }
    }


    public void generateAlert(){

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setIcon(R.drawable.ic_check)
                .setMessage(R.string.alert_msg)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if( i == DialogInterface.BUTTON_POSITIVE) {

            readXmlPullParser();

            List<Ponente> listp = pdao.getAll();
            List<Beacons> listb = bdao.getAll();
            List<Conections> listc = cdao.getAll();
            List<Dias> listd = ddao.getAll();
            List<Evento> liste = edao.getAll();
            List<Ubicacion> listu = udao.getAll();
            if (listp.size() > 0 && listb.size() > 0 && listc.size() > 0 && listd.size() > 0 && liste.size() > 0 && listu.size() > 0  ){

                Intent intent = new Intent(this, DetailEvent.class);
                startActivity(intent);
                finish();
            }

            else{
                Toast.makeText(this, R.string.msj_carga, Toast.LENGTH_LONG).show();
            }

        }

        if( i == DialogInterface.BUTTON_NEGATIVE) {

        }

    }
}
