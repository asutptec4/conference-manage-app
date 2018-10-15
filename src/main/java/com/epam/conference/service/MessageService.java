package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.MessageDao;
import com.epam.conference.entity.Message;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.MessageManager;

/**
 * Message service class is used for working with user {@link Message} objects.
 * Perform action via DAO layer classes.
 * 
 * @author Alexander Shishonok
 *
 */
public class MessageService {

    private static final MessageService INSTANCE = new MessageService();

    private MessageService() {
    }

    public static MessageService getInstance() {
	return INSTANCE;
    }

    /**
     * Return all message sent or received by user.
     * 
     * @param username
     *            user login.
     * @return an {@link List} contains all user messages.
     * @throws ConferenceAppServiceException
     *             if error is occurred to retrieve user messages from db.
     */
    public List<Message> findUserMessages(String username)
	    throws ConferenceAppServiceException {
	List<Message> messages = new ArrayList<>();
	try (MessageDao dao = new MessageDao()) {
	    messages = dao.findToUser(username);
	    messages.addAll(dao.findFromUser(username));
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to find messages", e);
	}
	messages.sort((m1, m2) -> (int) (m2.getTime() - m1.getTime()));
	return messages;
    }

    /**
     * Return users whose message doesn't read by admins.
     * 
     * @return set of user.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public Set<String> findUserWithUnreadMessage()
	    throws ConferenceAppServiceException {
	try (MessageDao dao = new MessageDao()) {
	    return dao.findAllUnread().stream()
		    .filter(message -> message.getTo()
			    .equals(MessageManager.EN
				    .getProperty("message.admin.login")))
		    .map(message -> message.getFrom())
		    .collect(Collectors.toSet());
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find unread messages", e);
	}
    }

    /**
     * Mark list messages is read by user.
     * 
     * @param messages
     *            an {@link List} contains messages.
     * @param username
     *            user login.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public void markIsRead(List<Message> messages, String username)
	    throws ConferenceAppServiceException {
	try (MessageDao dao = new MessageDao()) {
	    for (Message message : messages) {
		if (!message.isRead() && username.equals(message.getTo())) {
		    message.setRead(true);
		    dao.update(message);
		}
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to update messages status", e);
	}
    }

    /**
     * Send message to other user.
     * 
     * @param from
     *            message sender login.
     * @param to
     *            message receiver login.
     * @param text
     *            message text.
     * @return true if message was send.
     * @throws ConferenceAppServiceException
     *             if fail to add new message in db.
     */
    public boolean sendMessage(String from, String to, String text)
            throws ConferenceAppServiceException {
        boolean flag = false;
        Message message = new Message();
        message.setTo(to);
        message.setFrom(from);
        message.setText(text);
        try (MessageDao dao = new MessageDao()) {
            flag = dao.add(message);
        } catch (ConferenceAppDaoException e) {
            throw new ConferenceAppServiceException("Fail to add message to db",
        	    e);
        }
        return flag;
    }
}
