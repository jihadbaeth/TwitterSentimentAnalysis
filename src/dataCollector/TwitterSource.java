package dataCollector;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSource {
	
    
    
    public static void main(String[] args) throws TwitterException {
    	
    	
    	NLP.init();

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true);
    cb.setOAuthConsumerKey("Consumer Key");
    cb.setOAuthConsumerSecret("Consumer Secret");
    cb.setOAuthAccessToken("Access Token");
    cb.setOAuthAccessTokenSecret("Access Token Secret");

    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    StatusListener listener = new StatusListener() {

        public void onStatus(Status status) {
        	System.out.println("****** New Tweet Incoming ******");
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            System.out.println("Created At "+status.getCreatedAt());
            System.out.println("Being favoried by "+status.getFavoriteCount()+" users");
            System.out.println("get place "+status.getPlace());
            System.out.println("Follwoing/Followed By ratio is: "+status.getUser().getFollowersCount()/status.getUser().getFriendsCount());
            System.out.println("User verification status is: "+status.getUser().isVerified());
            System.out.println("Get Lang: "+status.getLang());
            
            System.out.println("The sentement Analysis Score is:"+ NLP.findSentiment(status.getText()));
        	System.out.println("****** End of Tweet Stream ******");

        }

        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        public void onScrubGeo(long userId, long upToStatusId) {
            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}
		
    };

    FilterQuery fq = new FilterQuery();
    String keywords[] = {"happy"};

    fq.track(keywords);

    twitterStream.addListener(listener);
    twitterStream.filter(fq);      
}
}
