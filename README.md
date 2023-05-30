# Analog Function Sampling and Recovery

This repository contains a Java program that demonstrates the sampling and recovery process for an analog function using the given equation. The program calculates the values of the analog and digital signals, performs signal recovery, and calculates the approximation error.

## Prerequisites

- Java Development Kit (JDK) installed on your machine
- Basic understanding of Java programming

## Usage

1. Clone the repository to your local machine using your preferred Git client or by running the following command:
   ```
   git clone <repository-url>
   ```

2. Open the project in your preferred Java IDE.

3. Open the `Main.java` file located in the root directory of the project.

4. Modify the following variables according to your requirements:
   - `function`: Update the function equation if desired.
   - `samplingPeriod`: Specify the sampling period.
   - `sampleCount`: Specify the number of samples.

5. Run the `Main` class to execute the program.

6. View the output in the console, which includes the details of the analog function, recovered signal values, and the approximation error.

## Program Structure

The program consists of the following main components:

- `Main`: This class contains the `main` method, which initializes an instance of the `AnalogFunction` class, performs the sampling and recovery process, and calculates the approximation error.

- `AnalogFunction`: This class represents an analog function. It contains methods to sample the function, recover the signal, and calculate the approximation error. The class also includes helper methods for extracting values from the equation and calculating the function values.

## Acknowledgements

This project is created for educational purposes and demonstrates the sampling and recovery process for analog functions. Thanks to the course instructors and contributors for their valuable guidance and support in developing this program.
