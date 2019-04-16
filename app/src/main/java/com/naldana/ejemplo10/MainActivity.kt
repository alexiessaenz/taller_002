package com.naldana.ejemplo10

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
//import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewAdapter: RecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    //var lista:RecyclerView?=null
    private val client = OkHttpClient()
    var money:ArrayList<Money>?=null
    var twoPane =  false
    var json:String = "{\"count\":9,\"coins\":[{\"name\":\"Dolar Estadounidense\",\"country\":\"El Salvador\",\"value\":1,\"value_us\":1,\"year\":2010,\"review\":\"El dólar estadounidense es la moneda oficial de Estados Unidos de América. Usualmente también se suele asociar el nombre empleado por la divisa con la circulación legal en ese país. Tras la ruptura del patrón oro en el año 1971, la moneda se convirtió, de facto, en una moneda fiat.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T05:21:49.289Z1dollarcoin.jpg\"},{\"name\":\"Lempira\",\"country\":\"Honduras\",\"value\":1,\"value_us\":0.041,\"year\":2000,\"review\":\"El Lempira (código ISO 4217: HNL) es la unidad monetaria de Honduras desde 1931. Se divide en 100 centavos. El organismo responsable de la emisión del Lempira es el Banco Central de Honduras.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:45:42.920Z1-Lempira.jpg\"},{\"name\":\"Belizean dollar\",\"country\":\"Belize\",\"value\":0.5,\"value_us\":0.24,\"year\":2000,\"review\":\"El dólar beliceño (en inglés Belize dollar o, simplemente, dollar) es la moneda de Belice. El código ISO 4217 es BZD. Normalmente se abrevia \$, o Bz\$ para diferenciarlo del dólar de los Estados Unidos y de otros tipos de dólares. Se subdivide en 100 cents.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:48:26.947Zbelice-50.jpg\"},{\"name\":\"Córdoba\",\"country\":\"Nicaragua\",\"value\":1,\"value_us\":0.03,\"year\":2000,\"review\":\"Córdoba nicaragüense, unidad monetaria de curso legal en Nicaragua dividida en 100 centavos. Su código ISO 4217 es NIO. Su circulación es controlada por el Banco Central de Nicaragua.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:49:41.950Zcodoba-1.jpg\"},{\"name\":\"Colon of Costa Rica\",\"country\":\"Costa Rica\",\"value\":1,\"value_us\":0.0017,\"year\":1999,\"review\":\"El Colón costarricense, llamado así en honor al Almirante Cristóbal Colón, es la moneda oficial de la República de Costa Rica en América Central. Su símbolo es una letra C atravesada por dos barras inclinadas verticales \\\"₡\\\". Su código ISO 4217 es CRC. Durante la colonia, en Costa Rica circulaba la moneda española y a partir de su independencia en 1821, la moneda usual fue primero el real y después el peso. A partir del 24 de octubre de 1896, en el gobierno de Rafael Iglesias Castro, se promulgó la ley del «Talón de Oro» que establecía como unidad monetaria el Colón, acogiendo una tendencia de la época con ocasión de las celebraciones del IV centenario del descubrimiento de América.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:50:42.756Zcosta-1.jpg\"},{\"name\":\"Quetzal\",\"country\":\"Guatemala\",\"value\":1,\"value_us\":1.14,\"year\":2000,\"review\":\"El quetzal es la actual unidad monetaria de uso legal en Guatemala. Fue creada al emitirse la Ley Monetaria del 26 de noviembre de 1924 mediante el Decreto número 879, durante el gobierno del presidente José María Orellana, año en que sustituyó al peso guatemalteco. El quetzal se divide en 100 centavos.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:51:32.921Zquetzal-1.jpg\"},{\"name\":\"Colon Salvadoreño\",\"country\":\"El Salvador\",\"value\":1,\"value_us\":0.11,\"year\":1990,\"review\":\"El colón es una unidad monetaria de El Salvador desde 1892. En 2001 fue sustituido progresivamente por el dólar estadounidense, aunque oficialmente no ha dejado de tener curso legal.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:54:02.599ZUncolon.jpg\"},{\"name\":\"Balboa\",\"country\":\"Panama\",\"value\":1,\"value_us\":1,\"year\":2000,\"review\":\"El balboa es una de las monedas de curso legal de Panamá, junto con el dólar estadounidense. Su código ISO 4217 es PAB. Está dividido en 100 centésimos.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:55:02.354Zbalboa-1.jpeg\"},{\"name\":\"Dolar Estadounidense\",\"country\":\"El Salvador\",\"value\":1,\"value_us\":1,\"year\":2010,\"review\":\"El dólar estadounidense es la moneda oficial de Estados Unidos de América. Usualmente también se suele asociar el nombre empleado por la divisa con la circulación legal en ese país. Tras la ruptura del patrón oro en el año 1971, la moneda se convirtió, de facto, en una moneda fiat.\",\"isAvailable\":true,\"img\":\"https://international-coins.herokuapp.com/uploads/2019-04-09T13:55:45.531Z1dollarcoin.jpg\"}]}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO (9) Se asigna a la actividad la barra personalizada
        setSupportActionBar(toolbar)
        //recyclerview.setBackground(Color.CYAN)

        money= ArrayList()



        for (i in 1..30){
            money?.add(Money(""+i+" Centavos",R.mipmap.ic_launcher))
        }

        /*money?.add(Money("10 Centavos",R.mipmap.ic_launcher))
        money?.add(Money("25 Centavos",R.mipmap.ic_launcher))
        money?.add(Money("1 Dolar",R.mipmap.ic_launcher))*/

        /*recyclerview.layoutManager= GridLayoutManager(this,3)
        recyclerview.adapter = RecyclerAdapter(money!!)*/

        viewManager = GridLayoutManager(this,3)

        viewAdapter = RecyclerAdapter(money!!)

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }






        // TODO (10) Click Listener para el boton flotante
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        // TODO (11) Permite administrar el DrawerLayout y el ActionBar
        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER
        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        nav_view.setNavigationItemSelectedListener(this)

        // TODO (20) Para saber si estamos en modo dos paneles
        if (fragment_content != null ){
            twoPane =  true
        }


        /*
         * TODO (Instrucciones)Luego de leer todos los comentarios añada la implementación de RecyclerViewAdapter
         * Y la obtencion de datos para el API de Monedas
         */
    }

    /*
    private fun runapi() {


        var request = Request.Builder()
            .url("https://international-coins.herokuapp.com/api/coin/")
            .get()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NTQ4NzM2MjgsImV4cCI6MTYxNzA4MTYyOH0.L90D8cHm18VjCgHEKKL_aCiWVcq_QQGBGWHvRB3cmo0")
            .addHeader("Content-Type", "application/json")
            .addHeader("cache-control", "no-cache")
            .addHeader("Postman-Token", "31420899-ba13-46d5-9724-09e500c4921b")
            .build()


        client.newCall(request).enqueue(object :Callback{
            /**
             * Called when the request could not be executed due to cancellation, a
             * connectivity problem or timeout. Because networks can fail during an
             * exchange, it is possible that the remote server accepted the request
             * before the failure.
             */
            override fun onFailure(request: Request?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * Called when the HTTP response was successfully returned by the remote
             * server. The callback may proceed to read the response body with [ ][Response.body]. The response is still live until its response body is
             * closed with `response.body().close()`. The recipient of the callback
             * may even consume the response body on another thread.
             *
             *
             * Note that transport-layer success (receiving a HTTP response code,
             * headers and body) does not necessarily indicate application-layer
             * success: `response` may still indicate an unhappy HTTP response
             * code like 404 or 500.
             */
            override fun onResponse(response: Response) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.d("APIJSON", response.body()?.string())
            }

        })

    }*/


    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.nav_camera -> {
                recyclerview.setBackgroundColor(Color.CYAN)

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}


