import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {
    return (
      <Navbar bg="white" expand="lg">
    
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse >
        <Nav className="mr-auto">
          <Nav.Link href="#home" style={{width: "200px" }}>Home</Nav.Link>
          <Nav.Link href="#link" style={{width: "100px" }} >Coaches</Nav.Link>
        </Nav>
        <Navbar.Brand href="#home" className="mr-auto" >React-Bootstrap</Navbar.Brand>
        <Nav className="mr-auto" id="basic-navbar-nav">
        <Nav.Link href="#link" style={{width: "200px" }}>Community</Nav.Link>
          <Nav.Link href="#link" style={{width: "200px" }}>Contact</Nav.Link>
          </Nav>
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

