import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {

    const mystyle = {

      marginLeft: "50px",
    };
    return (
      <Navbar bg="white" expand="lg">
    
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse >
        <Nav className="mr-auto">
          <Nav.Link href="#home" style={mystyle}>Home</Nav.Link>
          <Nav.Link href="#link" style={mystyle} >Coaches</Nav.Link>
          <Nav.Link href="#link" style={mystyle} ></Nav.Link>
          <Nav.Link href="#link" style={mystyle} ></Nav.Link>
        </Nav>
        <Navbar.Brand href="#home" style={{}} className="mr-auto" >React-Bootstrap</Navbar.Brand>
        <Nav className="mr-auto" id="basic-navbar-nav">
        <Nav.Link href="#link" style={mystyle}>Community</Nav.Link>
          <Nav.Link href="#link" style={mystyle}>Contact</Nav.Link>
          <Nav.Link href="#link" style={mystyle}>in</Nav.Link>
          </Nav>
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

