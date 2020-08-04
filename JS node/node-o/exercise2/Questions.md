### Questions to answer as part of the final exercise for CMMSC388O  
(just add your answers here after each question)  

1. What elements in HTML are `<View>` and `<Text>` analogous to?

`<Text>` - `<b>`,`<i>`

2.  What are the types of navigation we learned about in class?  How are they different?  
SwitchNavigator - the puspose of it is to only ever show one screen at a time.
StackNavigator - provides a way for the app to transition between screens where each new screen is placed on top of a stack.
MaterialTopTabNavigator - let programmers to swtich between different routes by tapping the route or swiping horizontally.
BottomTabNavigator - let programmers to switch betwen different routes with animation. 
DrawerNavigator - component that renders a navagation drawer which can be opnened and closed via gestures.
MaterialBottomTabNavigator - let programmers to switch between different routes with animation. 
CustomNavigator - the library implements a custom navigator called FluidNavigator: provides shared Element Transitions during navigation between screens using react-navigation. 


3.  What are the react component lifecycle methods? 
  Mounting – Birth of your component
  Update – Growth of your component
  Unmount – Death of your component
  
  render()
  componentDidMount()
  componentDidUpdate()
  componentWillUnmount()

4.  True or False. Data flows in one direction between react components.
True

5.  what does this line of code output:  
```javascript
  let name = "ovie"
  let team = {name: 'Capitals',
              [name]: 'Captain'}
  console.log(team)
```

[Object Object]

6.  True or False. Navigation in a mobile application is the same as in the browser.  
False

7. How does a component receive state?  
The state is defined in on eplace, parent component


8. True or False. A child can never communicate back to the parent in a react application?  
False

9.  True or false. Data persistence has to be external for a react native application. 
False

10. What did you enjoy most about the course? What did you enjoy the least?  
The projects are fun and really helpful to understand the lecture. Short time but a lot to learn!! 