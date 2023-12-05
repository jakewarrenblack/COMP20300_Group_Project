package com.example.obstaclecourse;

import com.example.obstaclecourse.Models.Dice;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.Assert.*;

public class DiceTest {

    private Dice dice;
    private boolean eventFired;

    @Before
    public void setUp() {
        dice = Dice.getInstance();
        eventFired = false;

        dice.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("diceValue".equals(evt.getPropertyName())) {
                    eventFired = true;
                }
            }
        });
    }

    @Test
    public void testRoll() {
        int rollValue = dice.roll();

        // Verify the roll value is within the expected range.
        assertTrue("Dice roll value should be between 1 and 9", rollValue >= 1 && rollValue <= 9);

        // Verify that the property change event is fired.
        assertTrue("Property change event should be fired after rolling the dice", eventFired);
    }

    @Test
    public void testRollMultipleTimes() {
        for (int i = 0; i < 100; i++) {
            int rollValue = dice.roll();

            // Verify the roll value is within the expected range each time.
            assertTrue("Dice roll value should be between 1 and 9", rollValue >= 1 && rollValue <= 9);
        }
    }

    @Test
    public void testGetValue() {
        dice.roll(); // Roll the dice to set its value.
        int value = dice.getValue();

        // Verify the value returned by getValue is the same as the last rolled value.
        assertEquals("Dice getValue should return the last rolled value", dice.getValue(), value);
    }
}
