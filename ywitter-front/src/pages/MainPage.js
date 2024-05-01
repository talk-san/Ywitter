import Feed from '../components/feed/Feed';
import { Searchbar } from '../components/searchbar/Searchbar';
import React from 'react';
import { Row, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import CustomNavbarComponent from '../components/navbar/CustomNavbar';
import WhoToFollow from "../components/users-card/WhoToFollow";

export const MainPage = () => {
  return (
    <Container fluid style={{ height: '100vh' }}>
      <Row>
        <Col xs={12} md={4}>
          <CustomNavbarComponent />
        </Col>
        <Col xs={12} md={4}>
          <Feed/>
        </Col>
        <Col xs={12} md={4}>
          <Searchbar />
          <WhoToFollow/>
        </Col>
      </Row>
    </Container>
  );
};


