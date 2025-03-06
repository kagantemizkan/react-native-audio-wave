# React Native Audio Wave

A native waveform visualization library for React Native applications. This package provides a customizable audio waveform component that can visualize audio files or sample data.

[![npm version](https://img.shields.io/npm/v/@kaannn/react-native-waveform.svg)](https://www.npmjs.com/package/@kaannn/react-native-waveform)
[![license](https://img.shields.io/npm/l/@kaannn/react-native-waveform.svg)](https://github.com/kagantemizkan/react-native-audio-wave/blob/main/LICENSE)

## Platform Support

- [x] Android (https://github.com/massoudss/waveformSeekBar) (Will be change to Reanimated 4)
- [ ] iOS (Planned)
- [ ] Web (Planned)

## Installation

```bash
npm install @kaannn/react-native-waveform
```

or

```bash
yarn add @kaannn/react-native-waveform
```

## Features

- Display waveform visualization for audio files
- Support for local and remote audio files
- Customizable appearance (colors, dimensions, etc.)
- Progress tracking for audio playback
- Direct sample data input support

## Usage

```jsx
import AudioWaveView  from '@kaannn/react-native-waveform';

// Usage with all props
<AudioWaveView
  style={{ flex: 1,width: '100%',height: '100%' }}
  progress={audio.progress}
  maxProgress={audio.lenght}
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

// Basic usage with a local audio file
<AudioWaveView
  style={{ width: '100%', height: 100 }}
  audioFileUri="file:///path/to/your/audio/file.mp3"
  progress={50} // 0-100 value representing playback progress
/>

// Using with remote URL
<AudioWaveView
  style={{ width: '100%', height: 100 }}
  audioFileUri="https://example.com/audio.mp3"
  waveProgressColor="#FF5500"
  waveBackgroundColor="#DDDDDD"
/>

// Using with sample data directly
<AudioWaveView
  style={{ width: '100%', height: 100 }}
  samples={[23, 56, 22, 99, 65, 41, 10, 80, 65, 32, 44, 29, 10, 85, 93, 42]}
/>
```

## Props

| Prop                | Type          | Default   | Description                                   |
| ------------------- | ------------- | --------- | --------------------------------------------- |
| audioFileUri        | string        | -         | Path to audio file (local or remote URL)      |
| samples             | number[]      | -         | Array of sample values to display as waveform |
| progress            | number        | 0         | Current progress (0-100)                      |
| maxProgress         | number        | 100       | Maximum progress value                        |
| visibleProgress     | number        | -         | Visible progress amount                       |
| waveWidth           | number        | 5         | Width of each wave bar                        |
| waveGap             | number        | 2         | Gap between wave bars                         |
| waveMinHeight       | number        | 5         | Minimum height of wave bars                   |
| waveCornerRadius    | number        | 2         | Corner radius of wave bars                    |
| wavePaddingTop      | number        | -         | Top padding for wave view                     |
| wavePaddingBottom   | number        | -         | Bottom padding for wave view                  |
| wavePaddingLeft     | number        | -         | Left padding for wave view                    |
| wavePaddingRight    | number        | -         | Right padding for wave view                   |
| waveBackgroundColor | string/number | '#LTGRAY' | Color of non-progressed wave bars             |
| waveProgressColor   | string/number | '#BLUE'   | Color of progressed wave bars                 |
| markerWidth         | number        | -         | Width of progress marker                      |
| markerColor         | string/number | '#GREEN'  | Color of progress marker                      |
| markerTextSize      | number        | -         | Size of marker text                           |
| markerTextColor     | string/number | '#RED'    | Color of marker text                          |
| markerTextPadding   | number        | -         | Padding for marker text                       |

## Roadmap

- iOS Support
  - Implement native iOS waveform visualization
  - Match feature parity with Android implementation
- Web Support
  - Create web-compatible implementation
- Performance Improvements
  - Implement Reanimated 4 for smoother animations
  - Optimize waveform rendering for large audio files
- Enhanced Features
  - Add audio playback controls
  - Support for real-time waveform visualization
  - Add audio recording with live waveform
  - Support for different waveform styles
- Documentation
  - Comprehensive API documentation
  - More usage examples
  - Demo app with various use cases

## Development

To develop and test the library locally:

1. Clone the repository

   ```bash
   git clone https://github.com/kagantemizkan/react-native-audio-wave.git
   cd react-native-audio-wave
   ```

2. Install dependencies

   ```bash
   yarn install
   ```

3. Run the example app

   ```bash
   yarn example start
   yarn example android
   ```

## Contributing

See the contributing guide to learn how to contribute to the repository and the development workflow.

## License

MIT
