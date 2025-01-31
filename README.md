# Simple Roullete Wheel

## Introduction Phase(0)

For my CPSC 210 project this term, I plan to create a **simple roulette wheel** application. The user will be prompted to choose either a number between **0 and 36** or a **color (red and black)**, then enter how much they want to bet. Each number will be assigned a color green, red, or black. Once the user makes their selection, a randomized ball will spin and land on a number. If the ball lands on the chosen number or choosen color, the user wins a prize, and **the prize amount will depend on the number or color they selected**.

Additionally, I plan to implement a **shop system** where players can spend their winnings to buy items. Every player will start with a *set amount of money*, which I will define at the beginning of the game and also I am planning to implement a **bank system** to *deposit the money and buy an item using the bank account balance*.

I'm excited to work on this roulette game project because it has several components that I need to implement, making it both challenging and fun. I'll put my best effort into creating the GUI. I also love watching people play roulette because it gives that thrilling, rollercoaster-like feeling.

## User Stories
- As a user, I want to be able to bet on a number that I want.
- As a user, I want to be able to bet on a color that I want.
- As a user, I want to be able to buy an item from the reward shop and add it to my backpack.
- As a user, I want to be able to see the item(s) that I owned in my backpack.
- As a user, I want to be able to sell an item from my backpack.
- As a user, I want to be able to rename my item in my backpack.
- As a user, when I select **Exit** *option* from the *Simple Roulette Wheel Application menu*, I want to be reminded to save my items in the backpack to file and have the *option* to do so or not. 
- As a user, when I select **Reload** *option* from the *Simple Roulette Wheel Application menu*, I want to be able to load the previous state of my Backpack from the file and resume my progress exactly as it was at an earlier time.

## Instructions for End User
- You can choose any bet from the roulette table and set an amount of money you want to bet.
- You can press the **ROLL** button to spin the roulette wheel. The result will be displayed in the result table.
- You will have balance that you can use to buy reward from store.
- You can press **STORE** button to access store and buy item from it, the item will be added to your backpack directly.
- You can press **BACKPACK** button to access item(s) you owned an you will have 3 options for each item you have.
- You can *sell* item(s) that you owned in your backpack for 80% of the original price.
- You can *rename* item(s) that you owned in your backpack.
- You can also *view* your item for the item's image.
- You can access **MENU** button to save the progress you made.
- You can access **MENU** button to load the progress you made before.

## Reference
- Using WorkRoomApp.java as the reference for data persistence
- Using Runnable from java standard library for notifying main UI
- GUI explenation : https://youtu.be/Kmgo00avvEw?si=vxT2fYZcf0HMcI9A

## Phase 4: Task 2
Fri Nov 29 01:58:26 PST 2024
Item added to backpack: Coke

Fri Nov 29 01:58:31 PST 2024
Item renamed: Coke -> Cola

Fri Nov 29 01:58:33 PST 2024
Item sold from backpack: Cola for $4000

Fri Nov 29 01:58:38 PST 2024
Item added to backpack: Coke

Fri Nov 29 01:58:46 PST 2024
Item renamed: Coke -> Pepsi

Fri Nov 29 01:58:48 PST 2024
Item sold from backpack: Pepsi for $4000
