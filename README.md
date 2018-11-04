# MiniEvoSim

The Mini Evo Sim (MES) is a small project I designed during software development class.

It models the process of evolution in a very simple manner by simulating differently
coloured "animals" being eaten by predators based on how well they match the environment (background) color.

The features required to model evolution are as following:
- Storage of genetic information
- Selection (Death of individual organisms)
- Recombination of genes to create the next generation
- Mutation to create new genes and stop evolutionary dead ends

All three aspects are featured in the MES.
Selection is done by fitness (eaten by predators) and randomness (died to disease or accidents).
Surviving animals recombine their genes in pairs to produce the next generation.
Occasionally the background colour can change, forcing the organisms to adapt to a new environment to escape predators.

Total development time: 8 days

Controls from left to right:
- Toggle button - Toggles visibility of genes and animal borders.
- Stop/Step-by-Step button - Stops animation, if already stopped, goes one step forward, showing which individuals died.
- Play buttons - Start the animation, different speeds are: 1 generation per second, 3 generations per second, 10 generations per second

Ian Kapischke
