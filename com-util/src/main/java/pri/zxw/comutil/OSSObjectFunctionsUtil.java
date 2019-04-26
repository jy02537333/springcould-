package pri.zxw.comutil;


import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * <p>
 * 该示例代码的执行过程是： 1. 创建一个Bucket（如果已经存在，则忽略错误码）； 2. 上传一个文件到OSS； 3. 下载这个文件到本地； 4.
 * 清理测试资源：删除Bucket及其中的所有Objects。
 */
public class OSSObjectFunctionsUtil {
    private static Logger logger = LoggerFactory.getLogger(OSSObjectFunctionsUtil.class);


    private static OSSObjectFunctionsUtil oSSObjectFunctions = null;
    protected static String ACCESS_ID;
    protected static String ACCESS_KEY;

    protected static String BUCKETNAME;

    protected static String OSS_HTTP_DOMAIN;

    protected static String OSS_HTTP_SUFFIX;

    protected static String OSS_ENDPOINT;

    protected static Integer MAX_SIZE;

    protected static OSSClient client;


    public OSSObjectFunctionsUtil() {
    }

    public OSSObjectFunctionsUtil(String accessId, String accessKey, String bucketName, String ossHttpDomain, String maxSize) {
        ACCESS_ID = accessId;
        ACCESS_KEY = accessKey;
        BUCKETNAME = bucketName;
        OSS_HTTP_DOMAIN = ossHttpDomain;
        OSS_ENDPOINT = "http://" + OSS_HTTP_DOMAIN;
        MAX_SIZE = Integer.valueOf(maxSize);
        OSS_HTTP_SUFFIX = "http://" + BUCKETNAME + "." + OSS_HTTP_DOMAIN + "/";
        client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
    }

    public static OSSObjectFunctionsUtil getIncetance() {
        ensureBucket(BUCKETNAME);

        if (oSSObjectFunctions == null) {
            oSSObjectFunctions = new OSSObjectFunctionsUtil();
        }

        return oSSObjectFunctions;
    }

    /*
     * public static void main(String[] args) throws Exception { String
     * bucketName = ACCESS_ID + "-object-test"; String key = "photo1.jpg";
     *
     * String uploadFilePath = "d:/temp/photo.jpg"; String downloadFilePath =
     * "d:/temp/photo1.jpg";
     *
     * // 使用默认的OSS服务器地址创建OSSClient对象。 OSSClient client = new
     * OSSClient(ACCESS_ID, ACCESS_KEY);
     *
     * ensureBucket(client, bucketName);
     *
     * try { setBucketPublicReadable(client, bucketName);
     *
     * uploadFile(client, bucketName, key, uploadFilePath);
     *
     * downloadFile(client, bucketName, key, downloadFilePath); } finally {
     * deleteBucket(client, bucketName); } }
     */

    /**
     * 创建Bucket
     *
     * @param bucketName
     * @throws OSSException
     * @throws ClientException
     */
    private static void ensureBucket(String bucketName) throws OSSException, ClientException {
        try {
            // 创建Bucket
            client.createBucket(bucketName);
            logger.info("create bucket success!");
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
                throw e;
            } else {
                // 如果Bucket已经存在，则忽略
                logger.info("the bucket name is already exists");
            }
        }
    }

    /**
     * 删除指定的Bucket下的Objects
     *
     * @param bucketName
     * @param key
     * @throws OSSException
     * @throws ClientException
     */
    public void deleteObject(String bucketName, String key) throws OSSException, ClientException {

        // 删除Object
        client.deleteObject(bucketName, key);
    }

    /**
     * 删除指定的Bucket和其中的Objects
     *
     * @param bucketName
     * @throws OSSException
     * @throws ClientException
     */
    public void deleteBucket(String bucketName) throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    /**
     * 把Bucket设置为所有人可读
     *
     * @param bucketName
     * @throws OSSException
     * @throws ClientException
     */
    public void setBucketPublicReadable(String bucketName) throws OSSException, ClientException {
        // 创建bucket
        // client.createBucket(bucketName);

        // 设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 上传文件
     *
     * @param
     * @param key
     * @param filename
     * @param fileLength
     * @param input
     * @throws OSSException
     * @throws ClientException
     */
    public String uploadFile(String key, String bucket, String filename, long fileLength, InputStream input)
            throws OSSException, ClientException {

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(fileLength);
        // 可以在metadata中标记文件类型
        // objectMeta.setContentType("image/jpeg");//上传的文件不一定是图片，所以这里不标记文件类型，如有需要，重载另加
        // InputStream input = new FileInputStream(file);
        bucket = StringUtils.isEmpty(bucket) ? BUCKETNAME : bucket;
        ensureBucket(bucket);
        client.putObject(bucket, key, input, objectMeta);
        return generateHTTPURL(bucket) + key;
    }

    /**
     * 实现一个 Object:File 的上传
     *
     * @param key  Object的key
     * @param file 上传Object的对象
     * @return 上传成功Object OSS地址
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public String uploadFile(String key, String bucket, String folder, File file)
            throws OSSException, ClientException, FileNotFoundException {

        // 获取指定文件的输入流
        InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(file.length());
        //格式化oss文件夹
        String filePath = key;
        String folderFormat = folder.trim().replace(" ", "");
        if (folderFormat.length() != 0 && !folderFormat.equals("") && folderFormat != null) {
            filePath = folderFormat + "/" + key;
        }

        // 上传Object.
        bucket = StringUtils.isEmpty(bucket) ? BUCKETNAME : bucket;
        ensureBucket(bucket);
        client.putObject(bucket, filePath, content, meta);
        URL url = client.generatePresignedUrl(BUCKETNAME, filePath, new Date());
        String s = url.toString();
        String substring = s.substring(0, s.indexOf("?"));
//        return generateHTTPURL(bucket) + key;
        return substring;
    }

    /**
     * 实现多个 Object:File[] 的上传
     *
     * @param key   Object的key
     * @param files 上传Object的对象
     * @return 上传成功Object OSS地址
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public void uploadFile(String key, String bucket, File[] files)
            throws OSSException, ClientException, FileNotFoundException {

        // 迭代File Object
        for (File file : files) {
            uploadFile(key, bucket, "", file);
        }

        // return OSS_HTTP_SUFFIX + key;
    }

    /**
     * 实现一个 Object:InputStream 的上传
     *
     * @param
     * @param key Object的key
     * @param
     * @return 上传成功Object OSS地址
     * @throws OSSException
     * @throws ClientException
     * @throws IOException
     * @throws NumberFormatException
     */
    public String uploadFile(String bucket, String key, InputStream inputStream)
            throws OSSException, ClientException, NumberFormatException, IOException {

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(Long.parseLong(String.valueOf(inputStream.available())));

        // 上传Object.
        bucket = StringUtils.isEmpty(bucket) ? BUCKETNAME : bucket;
        ensureBucket(bucket);
        client.putObject(bucket, key, inputStream, meta);

        return generateHTTPURL(bucket) + key;
    }

    /**
     * 下载文件 the key need to be the full name
     *
     * @param
     * @param key
     * @return InputStream
     * @throws OSSException
     * @throws ClientException
     */
    public InputStream downloadFile(String bucket, String key) throws OSSException, ClientException {
        bucket = StringUtils.isEmpty(bucket) ? BUCKETNAME : bucket;
        OSSObject object = client.getObject(bucket, key);
        return object.getObjectContent();
    }

    public static String generateHTTPURL(String bucket) {
        return "http://" + bucket + "." + OSS_HTTP_DOMAIN + "/";
//		return "http://" +""+ OSS_HTTP_DOMAIN + "/";
    }

    /**
     * @param url
     * @return
     */

    public String generatePresignedUrlByUrl(String url) {

        if (url == null || !url.startsWith("http")) {

            return null;

        }

        if (url.indexOf(".") == -1) {

            return null;

        }

        if (url.indexOf("/") == -1) {

            return null;

        }

        String bucketName;

        String key;

        String[] urlsBucket = url.split("\\.");

        if (url.startsWith("http://")) {

            bucketName = urlsBucket[0].substring("http://".length(), urlsBucket[0].length());

        } else {

            bucketName = urlsBucket[0].substring("https://".length(), urlsBucket[0].length());

        }

        key = url.substring(url.lastIndexOf("/") + 1, url.length());

        return generatePresignedUrl(bucketName, key);

    }

    /**
     * 生成私有bucket文件
     *
     * @param bucket
     * @param key
     * @return
     */
    public String generatePresignedUrl(String bucket, String key) {

        bucket = StringUtils.isEmpty(bucket) ? BUCKETNAME : bucket;
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = client.generatePresignedUrl(bucket, key, expiration);
        return url.toString();
    }
}
