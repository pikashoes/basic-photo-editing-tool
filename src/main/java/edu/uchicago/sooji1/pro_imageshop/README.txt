ProImageShop Project
Written by Sooji Yi

PROJECT REQUIREMENTS
---------------------
1. Eight image effects

    >> What I've done <<
    1. Saturate (included in base code)
    2. Darker (included in base code)
    3. Color/Hue changer slider
    4. Opacity changer slider
    5. Grayscale
    6. Flip vertically
    7. Flip horizontally
    8. Drop shadow

    >> Additional Features/Comments <<
    * Saturate & Darker are set as filters using the combo box.
    * The color changer and the opacity changer are sliders next to the pen selector
    * Grayscale, flip vertical, flip horizontal, drop shadow are all in the toolbox

2. Undo/Redo 10x

    >> What I've done <<
    I have tested the undo/redo for more than 10 effects and have been successful in
    reverting both ways.

    >> Additional Features/Comments <<
    * Some effects in the toolbox I have decided to not make undo-able using the Undo button. This is
      because I have allowed the user to "undo" the effect by clicking the button again.
    * The following effects can be undone by clicking the button but cannot be undone using the Undo button:
        1. Flip vertically
        2. Flip horizontally
        3. Drop shadow
    * Everything else can be undone/redone using the Undo/Redo buttons.


    * I have also added text when the mouse hovers over the toolbox buttons.

3. Bucket Tool

4. Select Tool

5. Eyedropper Tool

    >> What I've done <<
    I have added the eyedropper tool to the toolbar. Once you click it, you may click anywhere in the image view and
    pick a color.

    >> Additional Features/Comments <<
    * Picking a color will change the color picker to the new color.
    * This means that any drop shadow or pen drawing will be in the new color.

6. Dark grey user interface

    >> What I've done <<
    1. I made the menu black, and when you hover over each menu item, it changes color to indicate that the mouse is
       hovering over it.
    2. I made the toolbar under the menu gray, like in the pixlr editor.
    3. The various buttons, when selected, also change color.
    4. The background of the image view is also matching in the color scheme.

7. Toolbar

    >> What I've done <<
    1. I created a dialogue window that is NOT able to be closed. This will not close unless the main window is closed.
    2. The toolbar contains:
        - Bucket tool
        - Select tool
        - Eyedropper tool
        - Flip Vertical tool
        - Flip Horizontal tool
        - Drop Shadow tool
        - Grayscale tool
    3. Each tool in the toolbar has an icon.
    4. When you hover over any of these icons, text will appear over it to describe what each tool does.
    5. This toolbar is also in the same dark grey user interface theme.

CHALLENGES
-----------
1. Having the toolbar controller and the image shop controller communicate using Cc.java and figuring out how best
to do that using different functions was the first big challenge I had.

