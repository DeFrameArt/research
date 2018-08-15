package UploadImage.com.deframe.upload;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

public class Upload{
	public static Bucket getBucket(String bucket_name) {
		//final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJAHUWC7XJNPYNECA",
				"ewNIji34rhOz17kOVKBAs0tpvfdbFPCqg6XvCLWv");

		// create a client connection based on credentials
		//AmazonS3 s3 = new AmazonS3Client(credentials);
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_1).build();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}

	public static Bucket createBucket(String bucket_name) {
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess for
		// this example to work
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJAHUWC7XJNPYNECA",
				"ewNIji34rhOz17kOVKBAs0tpvfdbFPCqg6XvCLWv");

		// create a client connection based on credentials
		//AmazonS3 s3 = new AmazonS3Client(credentials);
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_1).build();
		
		Bucket b = null;
		if (s3.doesBucketExist(bucket_name)) {
			System.out.format("Bucket %s already exists.\n", bucket_name);
			b = getBucket(bucket_name);
		} else {
			try {
				b = s3.createBucket(bucket_name);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return b;
	}
	

    public static void main(String[] args)
    {
        final String USAGE = "\n" +
            "To run this example, supply the name of an S3 bucket and a file to\n" +
            "upload to it.\n" +
            "\n" +
            "Ex: PutObject <bucketname> <filename>\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bucket_name = args[0];
        String file_path = args[1];
        String key_name = Paths.get(file_path).getFileName().toString();

        System.out.format("\nCreating S3 bucket: %s\n", bucket_name);
        Bucket b = createBucket(bucket_name);
        if (b == null) {
            System.out.println("Error creating bucket!\n");
        } else {
            System.out.println("Done!\n");
        }
    

		AWSCredentials credentials = new BasicAWSCredentials("AKIAJAHUWC7XJNPYNECA",
				"ewNIji34rhOz17kOVKBAs0tpvfdbFPCqg6XvCLWv");

		// create a client connection based on credentials
		//AmazonS3 s3 = new AmazonS3Client(credentials);
		
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_1).build();
        try {
            s3.putObject(bucket_name, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
