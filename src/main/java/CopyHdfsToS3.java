import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// http://www.linuxidc.com/Linux/2014-01/94739.htm

public class CopyHdfsToS3 {

    /*
    private void put(String key, InputStream in, long length, boolean storeMetadata)
            throws IOException {

        try {
            S3Object object = new S3Object();
            object.setDataInputStream(in);
            object.setContentType("binary/octet-stream");
            object.setContentLength(length);
            if (storeMetadata) {
                object.addAllMetadata(METADATA);
            }
            s3Service.putObject(bucket, object);
        } catch (S3ServiceException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new S3Exception(e);
        }
    }

*/

    public static void main(String[] args){
        // connect s3
        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials("key", "skey"));

        Region cnNorth1 = Region.getRegion(Regions.CN_NORTH_1);
        s3Client.setRegion(cnNorth1);

        // put to s3
        File tmpFile = new File("localFile");

        //
        PutObjectRequest request = new PutObjectRequest("bucket1", "file1", tmpFile);

        Map<String, String> METADATA = new HashMap<String, String>();

        String FILE_SYSTEM_NAME = "fs";
        String FILE_SYSTEM_VALUE = "Hadoop";

        String FILE_SYSTEM_TYPE_NAME = "fs-type";
        String FILE_SYSTEM_TYPE_VALUE = "block";

        String FILE_SYSTEM_VERSION_NAME = "fs-version";
        String FILE_SYSTEM_VERSION_VALUE = "1";

        METADATA.put(FILE_SYSTEM_NAME, FILE_SYSTEM_VALUE);
        METADATA.put(FILE_SYSTEM_TYPE_NAME, FILE_SYSTEM_TYPE_VALUE);
        METADATA.put(FILE_SYSTEM_VERSION_NAME, FILE_SYSTEM_VERSION_VALUE);

        ObjectMetadata om = new ObjectMetadata();
        om.setUserMetadata(METADATA);
        request.setMetadata(om);

        s3Client.putObject(new PutObjectRequest("bucket1", "file1", tmpFile));
    }
}
