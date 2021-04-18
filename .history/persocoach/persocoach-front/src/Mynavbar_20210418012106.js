import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {

    const mystyle = {

      margin: "50px",
    };
    return (
      <Navbar bg="white" expand="lg">
    
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse >
        <Nav className="d-flex justify-content-start">
          <Nav.Link href="#home" style={mystyle}>Home</Nav.Link>
          <Nav.Link href="#link" style={mystyle} >Coaches</Nav.Link>
          <Nav.Link href="#link" style={mystyle} ></Nav.Link>
          <Nav.Link href="#link" style={mystyle} ></Nav.Link>
        </Nav>
        <Navbar.Brand href="#home" style={{marginRight : "50px" , MarginLeft :}} className="d-flex justify-content-center" >LOGO</Navbar.Brand>
        <Nav className="d-flex justify-content-end" id="basic-navbar-nav">
        <Nav.Link href="#link" style={mystyle}>Community</Nav.Link>
          <Nav.Link href="#link" style={mystyle}>Contact</Nav.Link>
          <Nav.Link href="#link" style={mystyle}>in</Nav.Link>
          </Nav>
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

