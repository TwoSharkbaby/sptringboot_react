import React from 'react';
import { styled } from 'styled-components';

const StyledFooterDiv = styled.div`
  border: 1px solid black;
  height: 300px;
`;

const Footer = () => {
  return (
    <StyledFooterDiv>
      <ul>
        <li>오시는길 : 압량읍</li>
        <li>전화번호 : 010-101-0001</li>
      </ul>
    </StyledFooterDiv>
  );
};

export default Footer;
