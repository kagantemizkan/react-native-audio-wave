import { requireNativeComponent, StyleSheet } from 'react-native';

const NativeAudioWaveView = requireNativeComponent('AudioWaveView');

type AudioWaveViewProps = {
  style?: any;
  audioSource?: string;
  progress?: number;
  maxProgress?: number;
  waveWidth?: number;
  waveGap?: number;
  waveMinHeight?: number;
  waveCornerRadius?: number;
  wavePaddingTop?: number;
  wavePaddingBottom?: number;
  wavePaddingLeft?: number;
  wavePaddingRight?: number;
  waveBackgroundColor?: string;
  waveProgressColor?: string;
  markerWidth?: number;
  markerColor?: string;
  markerTextColor?: string;
  markerTextSize?: number;
  markerTextPadding?: number;
  samples?: number[];
  [key: string]: any;
};

const AudioWaveView = ({
  style,
  audioSource,
  progress = 0,
  waveWidth = 5,
  waveGap = 2,
  waveBackgroundColor = '#DDDDDD',
  waveProgressColor = '#3F51B5',
  ...rest
}: AudioWaveViewProps) => {
  // Pass the audioSource directly to the native component as audioFileUri
  // No need for the samples state anymore

  return (
    <NativeAudioWaveView
      style={[styles.container, style]}
      progress={progress}
      waveWidth={waveWidth}
      waveGap={waveGap}
      waveBackgroundColor={waveBackgroundColor}
      waveProgressColor={waveProgressColor}
      audioFileUri={audioSource}
      {...rest}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    height: 80,
    width: '100%',
  },
});

export default AudioWaveView;
