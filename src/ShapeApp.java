import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ShapeApp extends JFrame {
    private JTextField shapeNameField;
    private JComboBox<String> shapeTypeComboBox;
    private JTextField dimensionField;
    private JButton drawButton;
    private JButton scaleButton;
    private JButton shadeButton;
    private JButton colorButton;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;

    private Map<String, Shape> shapeMap;
    private JPanel canvas;

    public ShapeApp() {
        setTitle("Shape Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        // Shape Name
        JLabel shapeNameLabel = new JLabel("Shape Name:");
        shapeNameField = new JTextField();

        // Shape Type
        JLabel shapeTypeLabel = new JLabel("Shape Type:");
        String[] shapeTypes = {"Rectangle", "Circle", "Triangle", "Ellipse", "Pentagon", "Hexagon"};
        shapeTypeComboBox = new JComboBox<>(shapeTypes);

        // Dimensions
        JLabel dimensionLabel = new JLabel("Dimensions:");
        dimensionField = new JTextField();

        // Buttons
        drawButton = new JButton("Draw");
        scaleButton = new JButton("Scale");
        shadeButton = new JButton("Shade");
        colorButton = new JButton("Change Color");
        saveButton = new JButton("Save");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        inputPanel.add(shapeNameLabel);
        inputPanel.add(shapeNameField);
        inputPanel.add(shapeTypeLabel);
        inputPanel.add(shapeTypeComboBox);
        inputPanel.add(dimensionLabel);
        inputPanel.add(dimensionField);
        inputPanel.add(drawButton);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        actionPanel.add(scaleButton);
        actionPanel.add(shadeButton);
        actionPanel.add(colorButton);
        actionPanel.add(saveButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        canvas = new JPanel();
        canvas.setPreferredSize(new Dimension(400, 400));

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(canvas, BorderLayout.SOUTH);

        // Initialize the shape map
        shapeMap = new HashMap<>();

        // Add action listeners
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                String dimensions = dimensionField.getText();
                String shapeType = (String) shapeTypeComboBox.getSelectedItem();
                drawShape(shapeName, dimensions, shapeType);
            }
        });

        scaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                scaleShape(shapeName);
            }
        });

        shadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                addShade(shapeName);
            }
        });

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                changeColor(shapeName);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                saveShape(shapeName);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                editShape(shapeName);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shapeName = shapeNameField.getText();
                deleteShape(shapeName);
            }
        });

        pack();
        setVisible(true);
    }

    private void drawShape(String shapeName, String dimensions, String shapeType) {
        Shape shape = null;

        if (shapeType.equals("Rectangle")) {
            shape = new CustomRectangle(shapeName, dimensions);
        } else if (shapeType.equals("Circle")) {
            shape = new CustomCircle(shapeName, dimensions);
        } else if (shapeType.equals("Triangle")) {
            shape = new CustomTriangle(shapeName, dimensions);
        } else if (shapeType.equals("Ellipse")) {
            shape = new CustomEllipse(shapeName, dimensions);
        } else if (shapeType.equals("Pentagon")) {
            shape = new CustomPentagon(shapeName, dimensions);
        } else if (shapeType.equals("Hexagon")) {
            shape = new CustomHexagon(shapeName, dimensions);
        }

        if (shape != null) {
            shapeMap.put(shapeName, shape);
            Graphics g = canvas.getGraphics();
            shape.draw(g);
        }
    }

    private void scaleShape(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            // Get the scale factor from the user
            String scaleInput = JOptionPane.showInputDialog(this, "Enter the scale factor:");
            double scaleFactor = Double.parseDouble(scaleInput);

            // Scale the shape's dimensions
            String[] parts = shape.dimensions.split(",");
            for (int i = 0; i < parts.length; i++) {
                int dimension = Integer.parseInt(parts[i].trim());
                int scaledDimension = (int) (dimension * scaleFactor);
                parts[i] = String.valueOf(scaledDimension);
            }
            shape.dimensions = String.join(",", parts);

            // Update the shape's properties based on the new dimensions
            if (shape instanceof CustomRectangle) {
                CustomRectangle rectangle = (CustomRectangle) shape;
                rectangle.width = Integer.parseInt(parts[0]);
                rectangle.height = Integer.parseInt(parts[1]);
            } else if (shape instanceof CustomCircle) {
                CustomCircle circle = (CustomCircle) shape;
                circle.diameter = Integer.parseInt(parts[0]);
            } else if (shape instanceof CustomTriangle) {
                CustomTriangle triangle = (CustomTriangle) shape;
                for (int i = 0; i < 3; i++) {
                    triangle.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    triangle.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            } else if (shape instanceof CustomEllipse) {
                CustomEllipse ellipse = (CustomEllipse) shape;
                ellipse.width = Integer.parseInt(parts[0]);
                ellipse.height = Integer.parseInt(parts[1]);
            } else if (shape instanceof CustomPentagon) {
                CustomPentagon pentagon = (CustomPentagon) shape;
                for (int i = 0; i < 5; i++) {
                    pentagon.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    pentagon.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            } else if (shape instanceof CustomHexagon) {
                CustomHexagon hexagon = (CustomHexagon) shape;
                for (int i = 0; i < 6; i++) {
                    hexagon.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    hexagon.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            }

            // Clear the canvas and redraw all shapes
            Graphics g = canvas.getGraphics();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (Shape s : shapeMap.values()) {
                s.draw(g);
            }
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }




    private void addShade(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            shape.addShade();
            Graphics g = canvas.getGraphics();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (Shape s : shapeMap.values()) {
                s.draw(g);
            }
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }

    private void changeColor(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            Color selectedColor = JColorChooser.showDialog(this, "Select Color", Color.BLACK);
            shape.changeColor(selectedColor);
            Graphics g = canvas.getGraphics();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (Shape s : shapeMap.values()) {
                s.draw(g);
            }
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }

    private void saveShape(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            shape.save();
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }

    private void editShape(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            shape.edit();
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }

    private void deleteShape(String shapeName) {
        Shape shape = shapeMap.get(shapeName);
        if (shape != null) {
            shapeMap.remove(shapeName);
            Graphics g = canvas.getGraphics();
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (Shape s : shapeMap.values()) {
                s.draw(g);
            }
        } else {
            System.out.println("Shape not found: " + shapeName);
        }
    }

    private abstract static class Shape {
        protected String shapeName;
        protected String dimensions;
        protected Color color;

        public Shape(String shapeName, String dimensions) {
            this.shapeName = shapeName;
            this.dimensions = dimensions;
            this.color = Color.BLACK;
        }

        public abstract void draw(Graphics g);

        public void scale() {
            System.out.println("Scaling shape: " + shapeName);
        }

        public void addShade() {
            System.out.println("Adding shade to shape: " + shapeName);
        }

        public void changeColor(Color color) {
            this.color = color;
            System.out.println("Changing color of shape: " + shapeName);
        }

        public void save() {
            System.out.println("Saving shape: " + shapeName);
        }

        public void edit() {
            System.out.println("Editing shape: " + shapeName);
        }
    }

    private static class CustomRectangle extends Shape {
        private int width;
        private int height;

        public CustomRectangle(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            String[] parts = dimensions.split(",");
            if (parts.length == 2) {
                this.width = Integer.parseInt(parts[0]);
                this.height = Integer.parseInt(parts[1]);
            }
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawRect(50, 50, width, height);
        }
    }

    private static class CustomCircle extends Shape {
        private int diameter;

        public CustomCircle(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            this.diameter = Integer.parseInt(dimensions);
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval(150, 150, diameter, diameter);
        }
    }

    private static class CustomTriangle extends Shape {
        private int[] xPoints;
        private int[] yPoints;

        public CustomTriangle(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            String[] parts = dimensions.split(",");
            if (parts.length == 6) {
                this.xPoints = new int[3];
                this.yPoints = new int[3];
                for (int i = 0; i < 3; i++) {
                    this.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    this.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            }
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }

    private static class CustomEllipse extends Shape {
        private int width;
        private int height;

        public CustomEllipse(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            String[] parts = dimensions.split(",");
            if (parts.length == 2) {
                this.width = Integer.parseInt(parts[0]);
                this.height = Integer.parseInt(parts[1]);
            }
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval(100, 250, width, height);
        }
    }

    private static class CustomPentagon extends Shape {
        private int[] xPoints;
        private int[] yPoints;

        public CustomPentagon(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            String[] parts = dimensions.split(",");
            if (parts.length == 10) {
                this.xPoints = new int[5];
                this.yPoints = new int[5];
                for (int i = 0; i < 5; i++) {
                    this.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    this.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            }
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawPolygon(xPoints, yPoints, 5);
        }
    }

    private static class CustomHexagon extends Shape {
        private int[] xPoints;
        private int[] yPoints;

        public CustomHexagon(String shapeName, String dimensions) {
            super(shapeName, dimensions);
            String[] parts = dimensions.split(",");
            if (parts.length == 12) {
                this.xPoints = new int[6];
                this.yPoints = new int[6];
                for (int i = 0; i < 6; i++) {
                    this.xPoints[i] = Integer.parseInt(parts[i * 2]);
                    this.yPoints[i] = Integer.parseInt(parts[i * 2 + 1]);
                }
            }
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawPolygon(xPoints, yPoints, 6);
        }
    }

    public static void main(String[] args) {
        new ShapeApp();
    }
}
