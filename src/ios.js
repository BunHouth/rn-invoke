const CGPoint = (value) => {
  return {
    type: 'CGPoint',
    value: value
  };
};

const CGRect = (value) => {
  return {
    type: 'CGRect',
    value: value
  };
};

module.exports = {
  CGPoint,
  CGRect
};
