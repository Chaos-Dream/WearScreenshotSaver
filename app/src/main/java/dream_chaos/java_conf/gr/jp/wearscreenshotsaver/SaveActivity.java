/*
 * Copyright 2016 Chaos Dream Application
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package dream_chaos.java_conf.gr.jp.wearscreenshotsaver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SaveActivity extends Activity {
    private static final String FILE_NAME = "/Pictures/Screenshots/Screenshot_wear_%s.png";

    private static final String DATE_FORMAT = "yyyyMMdd-kkmmss";

    private static final String TAG = SaveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);

        saveScreenshot(uri);

        finish();
    }

    private void saveScreenshot(Uri uri) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + String.format(FILE_NAME, getNowDate()));
            file.getParentFile().mkdir();

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), R.string.message_save, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), R.string.message_error, Toast.LENGTH_SHORT).show();
        }
    }

    private String getNowDate() {
        return DateFormat.format(DATE_FORMAT, Calendar.getInstance()).toString();
    }
}
