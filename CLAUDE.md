# Auto-sklearn Project Guide

## Project Overview
Auto-sklearn is an automated machine learning (AutoML) toolkit built on top of scikit-learn. It automatically searches for the right learning algorithm and hyperparameters for classification and regression tasks.

## Build & Test Commands
- `make inplace` - Build Cython extensions
- `make test` - Run all tests
- `make test-code` - Run code tests only
- `make test-coverage` - Run tests with coverage
- `make doc` - Build Sphinx documentation
- `nosetests test/` - Run tests directly

## Project Structure
- `autosklearn/` - Main package (automl.py, smbo.py, ensemble_builder.py, estimators.py)
- `autosklearn/pipeline/` - ML pipeline components (classifiers, regressors, preprocessors)
- `autosklearn/metalearning/` - Metalearning system for warm-starting optimization
- `autosklearn/evaluation/` - Model evaluation framework
- `autosklearn/ensembles/` - Ensemble construction
- `autosklearn/metrics/` - Scoring metrics
- `test/` - Test suite
- `examples/` - Usage examples
- `scripts/` - Metadata generation utilities

## Key Dependencies
- scikit-learn (>=0.19, <0.20), SMAC (==0.8), ConfigSpace (>=0.4.0, <0.5)
- XGBoost, pyrfr, pynisher, NumPy, SciPy, pandas

## Research Skills
Custom skills for ML research workflows are available in `.claude/skills/`.
