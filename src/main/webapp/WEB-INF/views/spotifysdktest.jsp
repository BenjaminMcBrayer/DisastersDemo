<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet"></link>
<title>Insert title here</title>
</head>
<body>
	<script>
	window.onSpotifyWebPlaybackSDKReady = () => {
		  const token = '[My Spotify Web API access token]';
		  const player = new Spotify.Player({
		    name: 'Web Playback SDK Quick Start Player',
		    getOAuthToken: cb => { cb(token); }
		  });
		const play = ({
		    spotify_uri,
		    playerInstance: {
		      _options: {
		        getOAuthToken,
		        id
		      }
		    }
		  }) => {
		    getOAuthToken(access_token => {
		      fetch(`https://api.spotify.com/v1/me/player/play?device_id=${deviceId}`, 
		          {
		        method: 'PUT',
		        body: JSON.stringify({ uris: [spotify_uri] }),
		        headers: {
		          'Content-Type': 'application/json',
		          'Authorization': `Bearer ${accessToken}`
		        },
		      });
		    });
		  };

		  play({
		    playerInstance: new Spotify.Player({ name: "..." }),
		    spotify_uri: 'spotify:track:7xGfFoTpQ2E7fRF5lN10tr',
		  });
		}
    	</script>
	<script src="https://sdk.scdn.co/spotify-player.js"></script>
</body>
</html> --%>
<!DOCTYPE html>
<html>
<head>
<title>Spotify Web Playback SDK Quick Start Tutorial</title>
</head>
<body>
	<h1>Spotify Web Playback SDK Quick Start Tutorial</h1>
	<h2>
		Open your console log:
		<code>View > Developer > JavaScript Console</code>
	</h2>

	<script src="https://sdk.scdn.co/spotify-player.js"></script>
	<script>
	window.onSpotifyWebPlaybackSDKReady = () => {
      const token = ${accessToken};
      const player = new Spotify.Player({
        name: 'Web Playback SDK Quick Start Player',
        getOAuthToken: cb => { cb(token); }
      });

      // Error handling
      player.addListener('initialization_error', ({ message }) => { console.error(message); });
      player.addListener('authentication_error', ({ message }) => { console.error(message); });
      player.addListener('account_error', ({ message }) => { console.error(message); });
      player.addListener('playback_error', ({ message }) => { console.error(message); });

      // Playback status updates
      player.addListener('player_state_changed', state => { console.log(state); });

      // Ready
      player.addListener('ready', ({ device_id }) => {
        console.log('Ready with Device ID', device_id);
      });

      // Not Ready
      player.addListener('not_ready', ({ device_id }) => {
        console.log('Device ID has gone offline', device_id);
      });

      // Connect to the player!
      player.connect();
    };
  </script>
</body>
</html>