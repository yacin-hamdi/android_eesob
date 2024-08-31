# Android EESOB: Object Detection Application

## Overview

Android EESOB is a simple Android application designed to deploy object detection models, specifically the YOLOv9c model, on Android devices. This app focuses on detecting various electronic and embedded systems, making it a practical tool for developers and electronics enthusiasts.

## Key Features

- **Seamless Model Integration:** Integrates the YOLOv9c model trained on the EESOB dataset for reliable detection of embedded systems and electronic components.
- **User-Friendly Interface:** Simple and intuitive design for real-time component identification using your smartphone's camera.
- **Optimized for Mobile:** Efficient performance on Android devices, ensuring smooth operation and accurate detection.

## Getting Started

### Prerequisites

- Android Studio
- Android device or emulator


### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yacin-hamdi/android_eesob.git
   ```

2. **Open the Project**

Open Android Studio and select **Open an existing project**, then choose the **android_eesob** directory.

3. **Configure Dependencies**

Ensure all required dependencies are included in your **build.gradle files**. You may need to sync your project with Gradle files after opening it.

4. **Run the App**

Connect your Android device or start an emulator, then build and run the application from Android Studio.

### Usage

- **Photo-Based Detection**: Capture a photo using the app, and the model will analyze the image to provide predictions for various electronic and embedded components.
- **Interface:** Navigate through the user-friendly interface to access the detection features.

## Development

### Training the Model

For details on how to train the YOLOv9c model, refer to the [YOLOv9c Model Training Instructions](https://github.com/yacin-hamdi/EESOB/blob/main/training.ipynb).

### Scraping Images

Instructions on how to scrape images for building the dataset are available in the [Dataset Preparation Guide](https://github.com/yacin-hamdi/EESOB/blob/main/web_scraper.ipynb).

## Future Plans

- Adding more features and improving the app’s accuracy in future updates.
- Expanding the model’s training period for enhanced performance.

## Contributing

I’m open to collaborations and contributions. If you're interested in working on this project or related projects, feel free to reach out!

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- YOLOv9c for object detection
- EESOB dataset for model training

## Contact

For any questions or suggestions, please reach out to me at [yacin.ha9@gmail.com].
