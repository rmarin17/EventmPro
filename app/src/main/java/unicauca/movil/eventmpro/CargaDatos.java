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
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class CargaDatos extends AppCompatActivity implements DialogInterface.OnClickListener {



    public static final String EXTRA_FILE = "pos";
    private static final int READ_REQUEST_CODE = 42;

    Uri archivo;
    ActivityCargaDatosBinding binding;
    Ponente p;
    PonenteDao dao;

    String img = "https://drive.google.com/open?id=0B1zSUe5TNtbmTFdUV2VUYkRsXzQ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_carga_datos);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_carga_datos);

        //String uri =  getIntent().getExtras().getString(EXTRA_FILE);
        //archivo = Uri.parse(uri);

        binding.setHandler(this);

        p = new Ponente();
        dao = new PonenteDao(this);

        //archivo = (TextView) findViewById();


        performFileSearch();


    }

    public void readXMLDOM() {

        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            //FileInputStream fis = openFileInput("cities.xml"); --> se cambia por el inputstream
            InputStream inputStream = getContentResolver().openInputStream(archivo);
            doc = db.parse(inputStream);
            //doc = db.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8)));
            NodeList ponente = doc.getElementsByTagName("Ponente");
            String result = "";
            for (int i = 0; i < ponente.getLength(); i++) {

                Node city = ponente.item(i);
                /*
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
                    Node node = nodeList.item(0);
                    node.getNodeValue();
                  }
                  */
                NodeList cityInfo = city.getChildNodes();
                for (int j = 0; j < cityInfo.getLength(); j++) {
                    Node info = cityInfo.item(j);
                    result += "<" + info.getNodeName() + ">" + info.getTextContent() + "</" + info.getNodeName() + ">\n";
                }
            }

            Toast.makeText(this, "Archivo: " +result, Toast.LENGTH_LONG).show();
            //txtXml.setText(result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    //region funciona lento
            /*
            List entries = new ArrayList();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();

            parser.require(XmlPullParser.START_TAG, null, "Ponentes");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("Ponente")) {
                    entries.add(readEntry(parser));
                } else {
                    skip(parser);
                }
            }
            */
    //endregion



            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    String condi = parser.getName();


                    switch (condi){
                        case "nombre":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            nombre = readText(parser);
                            sb.append(" Nombre: "+nombre+ "\n");
                            p.setNombre(nombre);
                            //dao.insert(p);
                            break;

                        case "apellidos":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            apellido = readText(parser);
                            sb.append(" Apellido: "+apellido+ "\n");
                            p.setApellidos(apellido);
                            //dao.insert(p);
                            break;

                        case "empresa":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            empresa = readText(parser);
                            sb.append(" Empresa: "+empresa+ "\n");
                            p.setEmpresa(empresa);
                            //p.setImagen(img);
                            //dao.insert(p);
                            break;

                        case "tipo":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            tipo = readText(parser);
                            sb.append(" Tipo: "+tipo+ "\n");
                            p.setTipo(tipo);

                            //dao.insert(p);
                            break;

                        case "imagen":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String imagen = readText(parser);
                            sb.append(" Imagen: "+imagen+ "\n");
                            p.setImagen(imagen);
                            //p.setImagen(img);
                            //dao.insert(p);
                            break;

                        case "estudios":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String estudio = readText(parser);
                            sb.append(" Estudios: "+estudio+ "\n");
                            p.setEstudios(estudio);
                            //p.setImagen(img);
                            //dao.insert(p);
                            break;

                        case "experiencia":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String experiencia = readText(parser);
                            sb.append(" Experiencia: "+experiencia+ "\n");
                            p.setExperiencia(experiencia);
                            //p.setImagen(img);
                            //dao.insert(p);
                            break;

                        case "formacioninternacional":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String forma = readText(parser);
                            sb.append(" Formacion Internacional: "+forma+ "\n");
                            p.setFormacioninternacional(forma);
                            //p.setImagen(img);
                            //dao.insert(p);
                            break;

                        case "habilidad":
                            parser.require(XmlPullParser.START_TAG, null, condi);
                            String habilidad = readText(parser);
                            sb.append(" Habilidad: "+habilidad+ "\n");
                            p.setHabilidad(habilidad);
                            //p.setImagen(img);
                            dao.insert(p);
                            break;

                    }


                }



                //region bad recorretodo
                /*if (eventType == XmlPullParser.START_DOCUMENT)
                    sb.append("[START]");
                else if (eventType == XmlPullParser.START_TAG && parser.getName() == "nombre")
                    sb.append("<" + parser.getText() + ">");
                else if (eventType == XmlPullParser.END_TAG && parser.getName() == "apellidos")
                    sb.append("</" + parser.getName() + ">");
                //else if (eventType == XmlPullParser.TEXT ) {
                   // sb.append(parser.getText());
                //}*/
                //endregion

                eventType = parser.next();


            }

            //dao.insert(p);
            //txtXml.setText(sb.toString());   --> para colocarlo en el edittext
            //Toast.makeText(this, "Archivo: " +sb.toString(), Toast.LENGTH_LONG).show();
            //binding.mostrar.setText(sb.toString());
           // dao.insert(p);
            //L.data.add(a);
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


    //region funcionalento parte 2
  /*  public static class Entry {
        public final String nombre;
        public final String apellido;
        public final String empresa;

        private Entry(String nombre, String apellido, String empresa) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.empresa = empresa;
        }
    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "Ponente");
        String nombre = null;
        String apellido = null;
        String empresa = null;
        String tipo = null;
        String mostrar;
        Ponente p = new Ponente();
        StringBuilder sb = new StringBuilder();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("nombre")) {
                nombre = readName(parser);
                p.setNombre(nombre);
                sb.append(nombre);
            }

            else if (name.equals("apellidos")) {
                apellido = readLastname(parser);
                p.setApellidos(apellido);
                sb.append(apellido);
            }
            else if (name.equals("empresa")){
                empresa = readCompany(parser);
                p.setEmpresa(empresa);
                sb.append(empresa);
            }

            else if (name.equals("tipo")){
                tipo = readType(parser);
                p.setTipo(tipo);
                sb.append(tipo);
            }
         else {
            skip(parser);
        }

            Toast.makeText(this, "Archivo: " +sb.toString(), Toast.LENGTH_LONG).show();
            binding.mostrar.setText(sb.toString());
    }
        return new Entry(nombre, apellido, empresa);
}

    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "nombre");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "nombre");
        return name;
    }


    private String readLastname(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "apellidos");
        String lastname = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "apellidos");
        return lastname;
    }


    private String readCompany(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "empresa");
        String company = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "empresa");
        return company;
    }


    private String readType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "tipo");
        String type = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "tipo");
        return type;
    }*/

    //endregion


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
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

    public void programacion(){

        Intent prog = new Intent(CargaDatos.this, Programacion.class);
        startActivity(prog);

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


            Intent inten = new Intent(this, DetailEvent.class);
            startActivity(inten);
            finish();
        }

        if( i == DialogInterface.BUTTON_NEGATIVE) {

        }

    }
}
