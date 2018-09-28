package com.DisastersDemo.soundtest;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//this is a tester class, it will play 10secs of the film.wav file as a Clip object
public class SoundTest {

	// had to put the full src/path for the file, not sure how to shorten it?
	final static String wavFile = "src/main/resources/static/film.wav";

	public static void main(String[] args) {
		// Clip object is a way to prebuffer the entire .wav file
		// Other way is AudioInputStream object which is more complicated, I'm looking  into it now!
		Clip audio;

		try {
			audio = AudioSystem.getClip();// LineUnavailableException

			audio.open(AudioSystem.getAudioInputStream(new File(wavFile)));	// LineUnavailableException, IOException, UnsupportedAudioFileExceptions

			audio.start();
			
			// Thread.sleep runs the program for 10000ms while the audio plays
			Thread.sleep(10000); // InterruptedException

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
