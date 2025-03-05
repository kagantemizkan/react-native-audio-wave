import { useState, useEffect } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import AudioWaveView from 'react-native-audio-wave'; // Adjust the path as needed

export default function App() {
  const [progress, setProgress] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [intervalId, setIntervalId] = useState<NodeJS.Timeout | null>(null);

  // Clean up interval on component unmount
  useEffect(() => {
    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [intervalId]);

  const handlePlay = () => {
    if (isPlaying) return;

    setIsPlaying(true);
    const id = setInterval(() => {
      setProgress((prev) => {
        if (prev >= 100) {
          clearInterval(id);
          setIsPlaying(false);
          return 0;
        }
        return prev + 0.5;
      });
    }, 100);

    setIntervalId(id);
  };

  const handlePause = () => {
    if (intervalId) {
      clearInterval(intervalId);
      setIntervalId(null);
    }
    setIsPlaying(false);
  };

  const handleReset = () => {
    if (intervalId) {
      clearInterval(intervalId);
      setIntervalId(null);
    }
    setIsPlaying(false);
    setProgress(0);
  };

  // URL to your audio file
  const audioUrl =
    'https://stage-sailatlas.s3.amazonaws.com/tasks/1/23/comments/3NxM6Lmtl34XUCWmO1goHNOWarQvHHBG.m4a';

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Audio Waveform</Text>

      <View style={styles.waveContainer}>
        <AudioWaveView
          style={styles.waveform}
          progress={progress}
          maxProgress={100}
          waveWidth={18}
          waveGap={2}
          waveMinHeight={25}
          waveCornerRadius={8}
          waveBackgroundColor="#E0E0E0"
          waveProgressColor="#2196F3"
          markerWidth={2}
          markerColor="#FF5722"
          markerTextColor="#000000"
          markerTextSize={10}
          markerTextPadding={2}
          audioSource={audioUrl}
        />
      </View>

      <Text style={styles.progressText}>Progress: {progress.toFixed(1)}%</Text>

      <View style={styles.buttonRow}>
        {!isPlaying ? (
          <Button title="Play" onPress={handlePlay} />
        ) : (
          <Button title="Pause" onPress={handlePause} />
        )}
        <Button title="Reset" onPress={handleReset} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  waveContainer: {
    width: '100%',
    height: 100,
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#E0E0E0',
    borderRadius: 8,
    padding: 10,
    backgroundColor: '#FAFAFA',
  },
  waveform: {
    flex: 1,
    width: '100%',
    height: '100%',
  },
  progressText: {
    fontSize: 16,
    marginBottom: 10,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    width: '100%',
  },
});
