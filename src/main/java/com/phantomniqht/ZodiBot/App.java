package com.phantomniqht.ZodiBot;

import java.time.OffsetDateTime;
import java.util.List;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class App extends ListenerAdapter
{
    public static void main( String[] args ) throws Exception
    {
    	System.out.println("Initializing");
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Reference.token).buildBlocking();
        jda.addEventListener(new App());
        System.out.println("Initialization Complete");
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	
    	/////////////
    	// OBJECTS //
    	/////////////
    	User objUser = event.getAuthor();
    	Guild objGuild = event.getGuild();
    	MessageChannel objMsgCh = event.getChannel();
    	Message objMsg = event.getMessage();
    	
    	//////////////
    	// COMMANDS //
    	//////////////
    	
    	// Ping
    	if (objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix+"ping"))
    	{
    		objMsgCh.sendMessage(objUser.getAsMention() + " Pong!").queue();
    	}
    	
    	// Help
    	if (objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "help"))
    	{
    		objMsgCh.sendMessage(objUser.getAsMention() + " IT is knocking at your DMs.").queue();
    		sendPrivateMessage(objUser, "**__ZodiBot - Version 1.0__**\n Developed by PhantomNiqht\n*Commands*\n!help - Sends this prompt to your DMs\n!ping - Returns \"Pong\"");
    	}
    	
    	// Sydney
    	if (objMsg.getContentRaw().equalsIgnoreCase(Reference.prefix + "syd"))
    	{
    		objMsgCh.sendMessage("Sydney is the bestest server owner in the world, and my Mommy!").queue();
    	}
    	
    	///////////////
    	// MOD TOOLS //
    	///////////////
		// Kick
    	if (objMsg.getContentRaw().startsWith(Reference.prefix + "kick"))
    	{
    		Member user = objMsg.getMember();
    		List targetUser = objMsg.getMentionedMembers();
    		if (!user.hasPermission(Permission.KICK_MEMBERS))
    		{
    			objMsgCh.sendMessage("You don't have permission to kick members").queue();
    		}
    		else if (user.hasPermission(Permission.KICK_MEMBERS))
    		{
    			if (!targetUser.isEmpty())
    			{
    				objGuild.getController().kick((Member) targetUser.get(0)).queue();
    				objMsgCh.sendMessage("Successfully kicked " + ((Member) targetUser.get(0)).getUser().getName()).queue();
    			
    			}
    			else if (targetUser.isEmpty())
    			{
    				objMsgCh.sendMessage("I could not find that user. Be sure to @mention them.\n__Syntax__\n`!kick <user>`").queue();
    			}
    		}
    	}
    	
    	// Warn
    	// no code yet
    	
    	// Soft Ban
    	/*
    	if (objMsg.getContentRaw().startsWith(Reference.prefix + "softban"))
    	{
    		Member user = objMsg.getMember();
    		List targetUser = objMsg.getMentionedMembers();
    		if (!user.hasPermission(Permission.BAN_MEMBERS))
    		{
    			objMsgCh.sendMessage("You don't have permission to ban members").queue();
    		}
    		else if (user.hasPermission(Permission.BAN_MEMBERS))
    		{
    			if (!targetUser.isEmpty())
    			{
    				objGuild.getController().ban((Member) targetUser.get(0), 1).queue();
    				objMsgCh.sendMessage("Successfully soft-banned " + ((Member) targetUser.get(0)).getUser().getName() + "...How do you sleep at night?").queue();
    				objGuild.getController().unban((User) (((Member) targetUser).getUser()));
    			
    			}
    			else if (targetUser.isEmpty())
    			{
    				objMsgCh.sendMessage("I could not find that user. Be sure to @mention them.\n__Syntax__\n`!softban <user>`").queue();
    			}
    		}
    	}
    	*/
    	
    	// Ban
    	if (objMsg.getContentRaw().startsWith(Reference.prefix + "ban"))
    	{
    		Member user = objMsg.getMember();
    		List targetUser = objMsg.getMentionedMembers();
    		if (!user.hasPermission(Permission.BAN_MEMBERS))
    		{
    			objMsgCh.sendMessage("You don't have permission to ban members").queue();
    		}
    		else if (user.hasPermission(Permission.BAN_MEMBERS))
    		{
    			if (!targetUser.isEmpty())
    			{
    				objGuild.getController().ban((Member) targetUser.get(0), 1).queue();
    				objMsgCh.sendMessage("Successfully banned " + ((Member) targetUser.get(0)).getUser().getName() + "...How do you sleep at night?").queue();
    			
    			}
    			else if (targetUser.isEmpty())
    			{
    				objMsgCh.sendMessage("I could not find that user. Be sure to @mention them.\n__Syntax__\n`!ban <user>`").queue();
    			}
    		}
    	}
    	
    	/////////////
    	// AUTOMOD //
    	/////////////
    	
    	// Curse Filter
    	if (objMsg.getContentStripped().equalsIgnoreCase("fuck"))
    	{
    		objMsgCh.sendMessage("You piece of Shit! You used a bad word!").queue();
    	}
    }
    
    public void sendPrivateMessage(User user, String content) {
        // openPrivateChannel provides a RestAction<PrivateChannel>
        // which means it supplies you with the resulting channel
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });
    }
}
