package theRhythmGirl.senddata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class SendData {
    public static Logger logger = LogManager.getLogger(SendData.class);
    public static int MINIMUM_FLOOR = 1;
    public static String CHARACTER_NAME = "the Rhythm Girl";

    public static void sendData(RunDetails run) {
        if (!run.characterName.equals(CHARACTER_NAME)){
            logger.info(String.format("Gameplay data was not send, only runs with \"%s\" are sent", CHARACTER_NAME));
            return;
        }
        if (run.maxFloor < MINIMUM_FLOOR) {
            logger.info(String.format("Gameplay data was not send, because the player did not reach floor %d.", MINIMUM_FLOOR));
            return;
        }
        Gson gson = new Gson();
        String postUrl = "http://therhythmgirldata.000webhostapp.com/";
        Net.HttpRequest req = (new HttpRequestBuilder()).newRequest().url(postUrl).method("POST").header("Content-Type", "application/json").content(gson.toJson(run)).build();
        logger.info("Anonymous gameplay data sent."/*req.getContent()*/);
        Gdx.net.sendHttpRequest(req, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                SendData.logger.info("http request response: " + httpResponse.getResultAsString());
            }

            public void failed(Throwable t) {
                t.printStackTrace();
                SendData.logger.info("http request failed: " + t);
            }

            public void cancelled() {
                SendData.logger.info("http request cancelled.");
            }
        });
    }

    public static void sendMockData(){
        RunDetails run = new RunDetails();
        run.version = "1.0";
        run.characterName = "the Rhythm Girl";
        run.sessionID = "Mock Session";
        run.ascensionLevel = 99;
        run.maxFloor = 99;
        run.elapsedTime = 99;
        run.score = 99;
        run.win = true;
        run.seed = "MOCK123";
        run.goldPerFloor = new ArrayList<>();
        run.itemsPurchased = new ArrayList<>();
        run.itemsPurged = new ArrayList<>();
        run.relicDetails = new ArrayList<>();
        run.cardDetails = new ArrayList<>();
        run.chosenCards = new ArrayList<>();
        run.notChosenCards = new ArrayList<>();
        sendData(run);
    }
}

/*
//index.php (runs on the postUrl)
<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = file_get_contents('php://input');
    $file_handle = fopen("run" . count(glob($directory . "*.json")) . ".json", 'w');
    fwrite($file_handle, $data);
    fclose($file_handle);
    echo "Run data received successfully";
}
else{
    echo (count(glob($directory . "*.json")) . " runs of the Rhythm Girl Mod were collected.");
}
?>
*/