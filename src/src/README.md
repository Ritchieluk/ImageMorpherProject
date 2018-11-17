# Program 3 README
___
Luke Ritchie,

Robert Wheaton,

CS335, Dr. Seales

Program 3: Phase 1

## Controller: JMorph + JMorphListener
### JMorph
- Contains a MouseActionListener that can move the points on each grid
- Takes in both grids' matricies and creates a transformation between them
- Has a button handler to upload images
### JMorphListener
- Handles all of the events for the grid; takes in a grid to know what to manipulate. 
## View: GriddedImage
- Takes an action listener so it can add it to itself
- Takes in an image to determine where to place the grid
- Contains a matrix of all of its points (TriangleGrid)
- When a dot is moved, the JMorphListener handles reanimation
- Build circles around each Point in the Triangle Grid
- Allows 
## Model: TriangleGrid
The triangle grid is a grid of a given amount of x and y values over a given width and height.
- For each x and y point combo it creates a Point
- It then draws triangles (Polygons) in the upper right and bottom left of the grid between the points
