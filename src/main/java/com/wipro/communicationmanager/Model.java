package com.wipro.communicationmanager;

public interface Model {

    enum Channel{ EMAIL,SMS,PUSH }
    record User(String userId, String name, String email, String smsNumber, String pushToken){}
    record SmsMetadata(String smsNumber, String text){}
    record EmailMetadata(String to, String subject, String body, String attachmentUrl){}
    record PushMetadata(String pushToken, String text){}

    record ChannelMessage(String userId, Channel channel, EmailMetadata emailMetadata, SmsMetadata smsMetadata, PushMetadata pushMetadata){
        public static ChannelMessage email(String userId, EmailMetadata emailMetadata){
            return new ChannelMessage(userId, Channel.EMAIL, emailMetadata,null,null);
        }
        public static ChannelMessage sms(String userId, SmsMetadata smsMetadata){
            return new ChannelMessage(userId, Channel.SMS,null,smsMetadata,null);
        }
        public static ChannelMessage push(String userId, PushMetadata pushMetadata){
            return new ChannelMessage(userId, Channel.PUSH,null,null,pushMetadata);
        }

    }

}
