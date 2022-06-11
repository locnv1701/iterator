import React from 'react';
import Carousel from 'nuka-carousel';
import { animated, to as interpolate } from '@react-spring/web';
import { IApplicantCard, IExtendedJobPost } from '../types/homeTypes';
import { Box, Chip } from '@material-ui/core';

interface Props {
  i: any;
  rot: any;
  scale: any;
  trans: any;
  objs: IApplicantCard[];
  x: any;
  y: any;
  bind: any;
  cards: any;
}

export const ApplicantCard = (props: Props) => {
  const { i, rot, scale, trans, objs, x, y, bind } = props;
  const { age, entry_level, experience_year, first_name, last_name, location, skills, image } =
    objs[i] || {};

  return (
    <>
      <animated.div
        style={{
          transform: interpolate([x, y], (x, y) => `translate3d(${x}px,${y}px,0)`),
        }}
      >
        <animated.div
          {...bind(i, objs[i])}
          style={{
            transform: interpolate([rot, scale], trans),
          }}
        >
          <div className="card">
            <Carousel>
              <img src={image} alt="profilePicture" />
            </Carousel>
            <div className="card-desc">
              <h2>{`${last_name} ${first_name} ${age}`}</h2>
              <h4>{entry_level}</h4>
              <h4>{experience_year} years of experience </h4>
              <h4>{location}</h4>

              {skills?.map((skill, index) => (
                <Box key={index} component="span" m={1}>
                  <Chip color="primary" size="small" label={skill.skill_name} />
                </Box>
              ))}
            </div>
          </div>
        </animated.div>
      </animated.div>
    </>
  );
};
