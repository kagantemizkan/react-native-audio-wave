import {
  requireNativeComponent,
  StyleSheet,
  type ViewStyle,
} from 'react-native';

const NativeAudioWaveView = requireNativeComponent('AudioWaveView');

// Extend the props for the native component
type AudioWaveViewProps = {
  style?: ViewStyle;
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
      // @ts-ignore: Suppress TS error for this line
      style={StyleSheet.compose(styles.container, style)}
      // Ensure style is passed correctly
      progress={progress}
      waveWidth={waveWidth}
      waveGap={waveGap}
      waveBackgroundColor={waveBackgroundColor}
      waveProgressColor={waveProgressColor}
      audioFileUri={audioSource} // Pass the audio source here
      {...rest} // Spread any other remaining props
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
