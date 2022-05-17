# Multilayer-Feed-Forward-Backpropagation-ANN-Tool
Development of Multilayer Feed Forward Backpropagation ANN Tool for Estimation Problems

# Project Description
This project aims to prediction problems with datas and when making estimates, the ANN topology with Back-Propagation with Multi-Layer Feed-Forward is used.

The data were normalized in the range 0-1
The data set is divided into 70% training and 30% testing

A multi-layer ANN topology with back-propagation with forward feed has been used.
There is only one hidden layer in this project. 
The number of nerve cells in the hidden layer can be changed by 2-20.
It has a Fixed Bias. The bias weights are randomly assigned between [0,1]. 
As a learning rule, the Extended Delta Bar Delta is used.
The learning coefficient is 0.01 and the momentum coefficient is 0.02.
The classical addition function and the sigmoid transfer function, which work as the sum of the multiplications in nerve cells is used.
The ANN Dec weights will be generated randomly between [0,1] at the beginning.
MSE (mean square error) mean square error is used to calculate the error value during the training process.
One of the criteria for terminating the educational process is the average absolute percentage error (MAPE). The training process is terminated when the MAPE value for the set of training observations is 3%.
Another of the termination criteria is that the maximum number of Epochs is 100. Whichever of the two criteria is met, the educational process is terminated.
As a result of the training process, network weights and network parameters is recorded.
An error value is plotted every 50 iterations during the training -the number of iterations is plotted.
Using the network weights obtained at the training stage, the average absolute percentage error ( MAPE) value is determined for the observations in the test set.

# Requirements
| Requirements      | Download Link                                        |
| ------------------| -----------------------------------------------------|
| JDK               | https://www.oracle.com/java/technologies/downloads/  |
| Libraries         | All of the libraries inside the lib folder.          |

# Usage
All of the packages are in the dist/lib folder. Because of that you don't need to unzip libraries.
```
git clone git@github.com:emregunar/Multilayer-Feed-Forward-Backpropagation-ANN-Tool.git
```
After you cloned the repo, open with your IDE and run the project. 
